package com.gardenary.domain.profile.service;

import com.gardenary.domain.avatar.dto.AvatarDto;
import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.avatar.entity.Avatar;
import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.avatar.repository.AvatarRepository;
import com.gardenary.domain.avatar.repository.MyAvatarRepository;
import com.gardenary.domain.profile.dto.ProfileDto;
import com.gardenary.domain.profile.dto.response.ProfileResponseDto;
import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.domain.profile.repository.ProfileRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.error.exception.AvatarApiException;
import com.gardenary.global.error.exception.ProfileApiException;
import com.gardenary.global.error.exception.UserApiException;
import com.gardenary.global.error.model.AvatarErrorCode;
import com.gardenary.global.error.model.ProfileErrorCode;
import com.gardenary.global.error.model.UserErrorCode;
import com.gardenary.global.properties.ConstProperties;
import com.gardenary.global.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MyAvatarRepository myAvatarRepository;
    private final AvatarRepository avatarRepository;
    private final ConstProperties constProperties;

    @Override
    public ProfileResponseDto getProfile(User user) {
        if (user == null) {
            throw new UserApiException(UserErrorCode.USER_NOT_FOUND);
        }

        Profile profile = profileRepository.findByUser(user);
        if (profile == null) {
            throw new ProfileApiException(ProfileErrorCode.PROFILE_NOT_FOUND);
        }

        return ProfileResponseDto.builder()
                .assetId(profile.getMyAvatar().getAvatar().getAssetId())
                .nickname(profile.getNickname())
                .build();
    }

    @Override
    public List<AvatarResponseDto> getAvatar(User user) {
        if (user == null) {
            throw new UserApiException(UserErrorCode.USER_NOT_FOUND);
        }

        List<Avatar> avatarList = avatarRepository.findAll();
        List<MyAvatar> myAvatarList = myAvatarRepository.findAllByUser(user);

        HashSet<Integer> set = new HashSet<>();
        for (MyAvatar myAvatar : myAvatarList) {
            set.add(myAvatar.getAvatar().getId());
        }

        List<AvatarResponseDto> avatarResponseDtos = new ArrayList<>();
        for (Avatar avatar : avatarList) {
            boolean isAcquired = set.contains(avatar.getId());
            avatarResponseDtos.add(AvatarResponseDto.builder()
                    .assetId(avatar.getAssetId())
                    .isAcquired(isAcquired)
                    .build());
        }

        return avatarResponseDtos;
    }

    @Transactional
    @Override
    public Boolean modifyNickname(User user, ProfileDto profileDto) {
        if (user == null) {
            throw new UserApiException(UserErrorCode.USER_NOT_FOUND);
        }

        if (profileDto == null || profileDto.getNickname() == null) {
            throw new ProfileApiException(ProfileErrorCode.NICKNAME_NOT_FOUND);
        }

        if (!ParameterUtil.checkStringSize(constProperties.getNicknameSize(), profileDto.getNickname())) {
            return false;
        }

        Profile profile = profileRepository.findByUser(user);
        if (profile == null) {
            throw new ProfileApiException(ProfileErrorCode.PROFILE_NOT_FOUND);
        }

        Profile checkNicknameProfile = profileRepository.findByNickname(profileDto.getNickname()).orElse(null);
        if (checkNicknameProfile != null) {
            throw new ProfileApiException(ProfileErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        profile.modifyNickname(profileDto.getNickname());
        return true;
    }

    @Transactional
    @Override
    public Boolean modifyAvatar(User user, AvatarDto avatarDto) {
        if (user == null) {
            throw new UserApiException(UserErrorCode.USER_NOT_FOUND);
        }

        if (avatarDto == null || avatarDto.getAssetId() == null) {
            return false;
        }

        Profile profile = profileRepository.findByUser(user);
        if (profile == null) {
            throw new ProfileApiException(ProfileErrorCode.PROFILE_NOT_FOUND);
        }

        Avatar avatar = avatarRepository.findByAssetId(avatarDto.getAssetId()).orElse(null);

        if (avatar == null) {
            throw new AvatarApiException(AvatarErrorCode.AVATAR_NOT_FOUND);
        }

        MyAvatar myAvatar = myAvatarRepository.findByUserAndAvatar(user, avatar).orElse(null);

        if (myAvatar == null) {
            throw new AvatarApiException(AvatarErrorCode.MYAVATAR_NOT_FOUND);
        }

        profile.modifyMyAvatar(myAvatar);

        return true;
    }
}
