package com.gardenary.domain.user.service;

import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.user.entity.User;

import java.util.List;

public interface UserService {
    List<AvatarResponseDto> getMyPage(User user);
}
