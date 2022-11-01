package com.gardenary.domain.tree.service;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.tree.dto.DiaryDto;
import com.gardenary.domain.tree.dto.MyTreeDto;
import com.gardenary.domain.tree.entity.Diary;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.tree.mapper.DiaryMapper;
import com.gardenary.domain.tree.mapper.MyTreeMapper;
import com.gardenary.domain.tree.repository.DiaryRepository;
import com.gardenary.domain.tree.repository.MyTreeRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.error.exception.GrowingPlantApiException;
import com.gardenary.global.error.exception.TreeApiException;
import com.gardenary.global.error.model.GrowingPlantErrorCode;
import com.gardenary.global.error.model.TreeErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor //생성자 주입. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성. AutoWired 불필요
@Slf4j //로깅 어노테이션
public class TreeServiceImpl implements TreeService {

    private final DiaryRepository diaryRepository;
    private final MyTreeRepository myTreeRepository;
    private final GrowingPlantRepository growingPlantRepository;

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
    public DiaryDto getDiary(LocalDateTime date, User user) {
        if(user == null) {
            return null;
        }

        DiaryDto result = DiaryMapper.mapper.toDto(diaryRepository.findByMyTree_UserAndDiaryDate(date, user)
                .orElseThrow(() -> new TreeApiException(TreeErrorCode.DIARY_NOT_FOUND)));
        return result;
    }

    @Override
    public List<DiaryDto> getDiaryList(int myTreeId, User user) {
        if(user == null) {
            return null;
        }
        MyTree myTree = myTreeRepository.findById(myTreeId)
                .orElseThrow(() -> new TreeApiException(TreeErrorCode.MY_TREE_NOT_FOUND));
        List<Diary> diaryList = diaryRepository.findAllByMyTree(myTree);

        List<DiaryDto> result = new ArrayList<>();
        for(Diary diary : diaryList) {
            result.add(DiaryMapper.mapper.toDto(diary));
        }
        return result;
    }

    @Override
    public List<MyTreeDto> getMyTreeList(User user) {
        //security에서 user 뽑아쓰기
        if(user == null) {
            return null;
        }

        List<MyTree> myTreeList = myTreeRepository.findAllByUser(user);

        List<MyTreeDto> result = new ArrayList<>();
        for(MyTree myTree : myTreeList) {
            result.add(MyTreeMapper.mapper.toDto(myTree));
        }
        return result;
    }
}
