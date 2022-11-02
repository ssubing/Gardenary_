package com.gardenary.domain.user.service;

import com.gardenary.domain.avatar.entity.Avatar;
import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.avatar.repository.AvatarRepository;
import com.gardenary.domain.avatar.repository.MyAvatarRepository;
import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.domain.profile.repository.ProfileRepository;
import com.gardenary.domain.user.entity.Role;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialServiceImpl implements SocialService {

    private final UserRepository userRepository;

    private final MyAvatarRepository myAvatarRepository;

    private final AvatarRepository avatarRepository;
    private final ProfileRepository profileRepository;

    @Override
    public User checkSignUp(String kakaoId) {

        User user = userRepository.findByKakaoId(kakaoId).orElse(null);

        // 회원 가입인 경우
        if (user == null) {
            String nickname = this.generateNickname();
            User newUser = User.builder()
                    .kakaoId(kakaoId)
                    .role(Role.USER)
                    .build();
            User savedUser = userRepository.save(newUser);
            Avatar avatar = avatarRepository.findById(1).orElseThrow();

            MyAvatar myAvatar = MyAvatar.builder()
                    .user(savedUser)
                    .avatar(avatar)
                    .build();
            myAvatarRepository.save(myAvatar);

            Profile newProfile = Profile.builder()
                    .user(savedUser)
                    .nickname(nickname)
                    .myAvatar(myAvatar)
                    .build();
            profileRepository.save(newProfile);

            // TODO : 경험치, 꽃, 나무, 현재식물 등 생성 필요

            return savedUser;
        }

        return user;
    }

    private String generateNickname() {
        Profile tmp = null;
        String nickname = "";
        do {
            nickname = RandomStringUtils.randomNumeric(5);
            tmp = profileRepository.findByNickname(nickname).orElse(null);
        } while (tmp != null);
        return nickname;
    }
}