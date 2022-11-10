package com.gardenary.domain.tree.service;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.exp.entity.Exp;
import com.gardenary.domain.exp.repository.ExpRepository;
import com.gardenary.domain.tree.dto.request.DiaryRequestDto;
import com.gardenary.domain.tree.dto.response.*;
import com.gardenary.domain.tree.entity.Diary;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.tree.entity.Tree;
import com.gardenary.domain.tree.repository.DiaryRepository;
import com.gardenary.domain.tree.repository.MyTreeRepository;
import com.gardenary.domain.tree.repository.TreeRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.error.exception.GrowingPlantApiException;
import com.gardenary.global.error.exception.TreeApiException;
import com.gardenary.global.error.model.GrowingPlantErrorCode;
import com.gardenary.global.error.model.TreeErrorCode;
import com.gardenary.global.properties.ConstProperties;
import com.gardenary.global.util.ParameterUtil;
import com.gardenary.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor //생성자 주입. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성. AutoWired 불필요
@Slf4j //로깅 어노테이션
public class TreeServiceImpl implements TreeService {

    private final DiaryRepository diaryRepository;
    private final MyTreeRepository myTreeRepository;
    private final TreeRepository treeRepository;
    private final GrowingPlantRepository growingPlantRepository;
    private final ConstProperties constProperties;
    private final RedisService redisService;
    private final ExpRepository expRepository;

    @Override
    @Transactional
    public CompleteTreeInfoResponseDto createMyTree(User user) {
        //나무 경험치 체크
        int totalExp = Integer.parseInt(redisService.getStringValue(user.getKakaoId()+"treeExp"));
        if(totalExp == 0 || (totalExp % constProperties.getExpLevelup()) != 0) {
            throw new TreeApiException(TreeErrorCode.NOT_ENOUGH_EXP);
        }

        //현재 식물 가져오기
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
        if(growingPlant == null) {
            return null;
        }
        if(growingPlant.getId() == 0) {
            throw new GrowingPlantApiException(GrowingPlantErrorCode.GROWING_PLANT_NOT_FOUND);
        }

        if(growingPlant.getMyTree().getDoneAt() == null) {
            return null;
        }
        String name = growingPlant.getMyTree().getTree().getName();
        String assetId = growingPlant.getMyTree().getTree().getAssetId();

        //나의 나무 생성 및 현재 나무 수정
        MyTree myTree = MyTree.builder()
                .tree(randomTree())
                .user(user)
                .doneAt(null)
                .build();
        myTree = myTreeRepository.save(myTree);
        growingPlant.modifyMyTree(myTree);

        return CompleteTreeInfoResponseDto.builder()
                .assetId(assetId)
                .name(name)
                .build();
    }

    @Override
    @Transactional
    public MakeDiaryResponseDto createDiary(User user, DiaryRequestDto diaryRequestDto) {
        if(user == null || diaryRequestDto == null ||
                diaryRequestDto.getContent() == null || diaryRequestDto.getCreatedAt() == null) {
            return null;
        }
        if(!ParameterUtil.checkStringSize(constProperties.getContentSize(), diaryRequestDto.getContent())) {
            return null;
        }


        Diary diary = diaryRepository.findTop1ByMyTree_UserOrderByCreatedAtDesc(user)
                .orElse(null);
        //가장 최근 다이어리가 존재할 때
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime lastTime = time.minusDays(10);
        LocalDateTime startTime;
        LocalDateTime endTime;
        if(time.getHour() >= 3){
            startTime = time.withHour(3).withMinute(0).withSecond(0);
        } else {
            startTime = time.withHour(3).withMinute(0).withSecond(0).minusDays(1);
        }
        endTime = startTime.plusDays(1).minusSeconds(1);

        if(diary != null) {
            lastTime = diary.getCreatedAt();

            //오늘치 이미 작성했으면 다이어리 저장하고 경험치 리턴
            if(lastTime.isAfter(startTime) && lastTime.isBefore(endTime)) {
                GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
                if(growingPlant == null) {
                    return null;
                }

                LocalDateTime date = diaryRequestDto.getCreatedAt()
                        .withHour(0).withMinute(0).withSecond(0).withNano(0);

                Diary savedDiary = diaryRepository.save(Diary.builder()
                        .diaryDate(date)
                        .myTree(growingPlant.getMyTree())
                        .content(diaryRequestDto.getContent())
                        .build());
                if(savedDiary.getId() == 0) {
                    return null;
                }

                int treeExp = Integer.parseInt(redisService.getStringValue(user.getKakaoId()+"treeExp"));
                return MakeDiaryResponseDto.builder()
                        .isItem(false)
                        .totalExp(treeExp)
                        .build();
            }
        }
        //다이어리 생성
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
        if(growingPlant == null) {
            return null;
        }

        LocalDateTime date = diaryRequestDto.getCreatedAt()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        Diary savedDiary = diaryRepository.save(Diary.builder()
                .diaryDate(date)
                .myTree(growingPlant.getMyTree())
                .content(diaryRequestDto.getContent())
                .build());
        if(savedDiary.getId() == 0) {
            return null;
        }

        //가장 최근 다이어리가 없거나(한 번도 작성한 적이 없을 때) 오늘치 작성안했으면 동작 후 경험치 리턴
        //1. 경험치 증가
        int treeExp = Integer.parseInt(redisService.getStringValue(user.getKakaoId()+"treeExp"));
        int totalExp = treeExp + constProperties.getExpTree();
        redisService.setValue(user.getKakaoId()+"treeExp", totalExp+"");

        //1-2. 100이면 DoneAt 변경
        if(totalExp % constProperties.getExpLevelup() == 0) {
            growingPlant.getMyTree().modifyDoneAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        }

        //2. 경험치 기록 저장
        Exp exp = Exp.builder()
                .expAmount(constProperties.getExpTree())
                .diaryId(savedDiary.getId())
                .type(false)
                .user(user)
                .build();
        expRepository.save(exp);

        //3. 연속 작성일 수 증가
        if(lastTime.isBefore(startTime.minusDays(1))) {
            growingPlant.modifyDiaryDays(1);
        } else {
            growingPlant.modifyDiaryDays(growingPlant.getDiaryDays() + 1);
        }
        boolean isItem = false;
        if(growingPlant.getDiaryDays()%2 == 0) {
            isItem = true;
        }

        //4. 리턴
        return MakeDiaryResponseDto.builder()
                .isItem(isItem)
                .totalExp(totalExp)
                .build();
    }

    @Override
    public List<DiaryResponseDto> getDateDiaryList(LocalDateTime date, User user) {
        if(user == null) {
            return null;
        }
        date = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Diary> diaryList = diaryRepository.findAllByMyTree_UserAndDiaryDate(user, date);
        List<DiaryResponseDto> result = new ArrayList<>();
        for(Diary diary : diaryList) {
            result.add(DiaryResponseDto.builder()
                    .content(diary.getContent())
                    .diaryDate(diary.getDiaryDate())
                    .build());
        }
        return result;
    }

    @Override
    public DiaryListResponseDto getDiaryList(int myTreeId, User user) {
        if(user == null) {
            return null;
        }
        MyTree myTree = myTreeRepository.findById(myTreeId)
                .orElseThrow(() -> new TreeApiException(TreeErrorCode.MY_TREE_NOT_FOUND));
        List<Diary> diaryList = diaryRepository.findAllByMyTree(myTree, Sort.by(Sort.Direction.ASC, "diaryDate"));

        List<DiaryResponseDto> result = new ArrayList<>();

        for(Diary diary : diaryList) {
            result.add(DiaryResponseDto.builder()
                    .diaryDate(diary.getDiaryDate())
                    .content(diary.getContent()).build());
        }
        return DiaryListResponseDto.builder()
                .diaryList(result)
                .assetId(myTree.getTree().getAssetId())
                .name(myTree.getTree().getName())
                .startDate(myTree.getCreatedAt())
                .endDate(myTree.getDoneAt())
                .build();
    }

    @Override
    public List<TreeResponseDto> getMyTreeList(User user) {
        //security에서 user 뽑아쓰기
        if(user == null) {
            return null;
        }
        List<Tree> treeList = treeRepository.findAll();
        List<MyTree> myTreeList = myTreeRepository.findAllByUser(user);
        HashSet<Integer> set = new HashSet<>();
        for(MyTree myTree : myTreeList) {
            if(myTree.getDoneAt() == null) {
                continue;
            }
            set.add(myTree.getTree().getId());
        }

        List<TreeResponseDto> result = new ArrayList<>();
        for(Tree tree : treeList) {
            boolean isAcquired = set.contains(tree.getId());
            result.add(TreeResponseDto.builder()
                    .assetId(tree.getAssetId())
                    .content(tree.getContent())
                    .name(tree.getName())
                    .isAcquired(isAcquired)
                    .build());
        }
        return result;
    }

    public Tree randomTree() {
        int num = (int)(Math.random()*constProperties.getTreeSize() + 1);
        Tree tree = treeRepository.findById(num)
                .orElseThrow(() -> new TreeApiException(TreeErrorCode.TREE_NOT_FOUND));
        return tree;
    }
}
