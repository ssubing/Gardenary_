package com.gardenary.domain.tree.service;

import com.gardenary.domain.tree.dto.DiaryDto;
import com.gardenary.domain.tree.dto.MyTreeDto;
import com.gardenary.domain.tree.dto.response.DiaryListResponseDto;
import com.gardenary.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TreeService {

    boolean createMyTree(User user);

    boolean updateCurTree(User user);

    DiaryDto getDiary(LocalDateTime date, User user);

    DiaryListResponseDto getDiaryList(int myTreeId, User user);

    List<MyTreeDto> getMyTreeList(User user);
}
