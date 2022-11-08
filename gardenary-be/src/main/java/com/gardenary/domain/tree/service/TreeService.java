package com.gardenary.domain.tree.service;

import com.gardenary.domain.tree.dto.request.DiaryRequestDto;
import com.gardenary.domain.tree.dto.response.DiaryListResponseDto;
import com.gardenary.domain.tree.dto.response.DiaryResponseDto;
import com.gardenary.domain.tree.dto.response.MakeDiaryResponseDto;
import com.gardenary.domain.tree.dto.response.TreeResponseDto;
import com.gardenary.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TreeService {

    boolean createMyTree(User user);

    boolean updateCurTree(User user);

    MakeDiaryResponseDto createDiary(User user, DiaryRequestDto diaryRequestDto);

    List<DiaryResponseDto> getDateDiaryList(LocalDateTime date, User user);

    DiaryListResponseDto getDiaryList(int myTreeId, User user);

    List<TreeResponseDto> getMyTreeList(User user);
}
