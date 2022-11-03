package com.gardenary.domain.user.service;

import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.avatar.entity.Avatar;
import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.avatar.repository.AvatarRepository;
import com.gardenary.domain.avatar.repository.MyAvatarRepository;
import com.gardenary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final MyAvatarRepository myAvatarRepository;

    private final AvatarRepository avatarRepository;


    @Override
    public List<AvatarResponseDto> getMyPage(User user) {
        if (user == null) {
            return null;
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
}