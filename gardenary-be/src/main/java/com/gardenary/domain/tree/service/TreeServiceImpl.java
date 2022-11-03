package com.gardenary.domain.tree.service;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.tree.dto.response.DiaryListResponseDto;
import com.gardenary.domain.tree.dto.response.DiaryResponseDto;
import com.gardenary.domain.tree.dto.response.TreeResponseDto;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public boolean createMyTree(User user) {
        //현재 식물 가져오기
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
        if(growingPlant.getId() == 0) {
            throw new GrowingPlantApiException(GrowingPlantErrorCode.GROWING_PLANT_NOT_FOUND);
        }
        return false;
    }

    @Override
    public boolean updateCurTree(User user) {
        return false;
    }

    @Override
    public List<DiaryResponseDto> getDateDiaryList(LocalDateTime date, User user) {
        if(user == null) {
            return null;
        }
        date = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Diary> diaryList = diaryRepository.findAllByMyTree_UserAndDiaryDate(date, user);
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

    private Tree randomTree() {
        int num = (int)(Math.random()*constProperties.getTreeSize() + 1);
        Tree tree = treeRepository.findById(num)
                .orElseThrow(() -> new TreeApiException(TreeErrorCode.TREE_NOT_FOUND));
        return tree;
    }
}
