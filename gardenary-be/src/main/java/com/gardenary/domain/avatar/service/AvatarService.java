package com.gardenary.domain.avatar.service;

import com.gardenary.domain.avatar.dto.response.AvatarListResponseDto;
import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.user.entity.User;

public interface AvatarService {

    AvatarListResponseDto getNewAvatar (User user, int type);

}
