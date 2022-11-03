package com.gardenary.domain.profile.service;

import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.profile.dto.ProfileDto;
import com.gardenary.domain.profile.dto.response.ProfileResponseDto;
import com.gardenary.domain.user.entity.User;

import java.util.List;

public interface ProfileService {

    ProfileResponseDto getProfile(User user);

    List<AvatarResponseDto> getAvatar(User user);

    Boolean modifyNickname(User user, ProfileDto profileDto);

}
