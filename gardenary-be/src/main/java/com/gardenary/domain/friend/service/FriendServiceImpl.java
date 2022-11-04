package com.gardenary.domain.friend.service;

import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.avatar.repository.MyAvatarRepository;
import com.gardenary.domain.friend.dto.response.FriendResponseDto;
import com.gardenary.domain.friend.entity.Friend;
import com.gardenary.domain.friend.repository.FriendRepository;
import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.domain.profile.repository.ProfileRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import com.gardenary.global.error.exception.AvatarApiException;
import com.gardenary.global.error.exception.UserApiException;
import com.gardenary.global.error.model.AvatarErrorCode;
import com.gardenary.global.error.model.UserErrorCode;
import com.gardenary.global.properties.EncryptProperties;
import com.gardenary.global.util.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor //생성자 주입. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성. AutoWired 불필요
@Slf4j //로깅 어노테이션
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final MyAvatarRepository myAvatarRepository;
    private final EncryptProperties encryptProperties;
    @Override
    public boolean createFriend(User user, String encryptUserId) {
        if(user == null || encryptUserId == null) {
            return false;
        }
        String decryptUserId = null;
        try {
            decryptUserId = Encrypt.decryptAES256(encryptProperties.getEncryptKey(), encryptProperties.getEncryptIv(), encryptUserId);
        } catch (Exception e) {
            return false;
        }
        User following = userRepository.findById(UUID.fromString(decryptUserId))
                .orElseThrow(() -> new UserApiException(UserErrorCode.USER_NOT_FOUND));
        Friend friend = friendRepository.save(Friend.builder().following(following).follower(user).build());
        if(friend.getId() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFriend(User user, int friendId) {
        if(user == null || friendId == 0) {
            return false;
        }
        Friend friend = friendRepository.findById(friendId)
                        .orElse(null);
        if(friend == null || !friend.getFollower().getId().equals(user.getId())) {
            return false;
        }
        friendRepository.deleteById(friendId);
        return true;
    }

    @Override
    public FriendResponseDto findUser(User user, String nickname) {
        if(user == null || nickname == null) {
            return null;
        }
        Profile profile = profileRepository.findByNickname(nickname)
                .orElse(null);
        if(profile == null) {
            return null;
        }
        if(profile.getUser().getId().equals(user.getId())) {
            return null;
        }

        User following = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new UserApiException(UserErrorCode.USER_NOT_FOUND));
        Friend friend = friendRepository.findByFollowingAndFollower(following, user)
                .orElse(null);
        MyAvatar myAvatar = myAvatarRepository.findByUser(following)
                .orElseThrow(() -> new AvatarApiException(AvatarErrorCode.MYAVATAR_NOT_FOUND));

        String encryptUserId = null;
        try {
            encryptUserId = Encrypt.encryptAES256(encryptProperties.getEncryptKey(), encryptProperties.getEncryptIv(), following.getId().toString());
        } catch (Exception e) {
            return null;
        }
        return FriendResponseDto.builder()
                .enCryptUserId(encryptUserId)
                .friendId(friend==null?null:friend.getId())
                .assetId(myAvatar.getAvatar().getAssetId())
                .nickname(nickname)
                .build();
    }

    @Override
    public List<FriendResponseDto> getFollowingList(User user) {
        if(user == null) {
            return null;
        }
        List<Friend> friendList = friendRepository.findAllByFollower(user);
        List<FriendResponseDto> result = new ArrayList<>();
        for(Friend friend : friendList) {
            Profile profile = profileRepository.findByUser(friend.getFollowing());
            if(profile == null) {
                continue;
            }

            String encryptUserId = null;
            try {
                encryptUserId = Encrypt.encryptAES256(encryptProperties.getEncryptKey(), encryptProperties.getEncryptIv(), profile.getUser().getId().toString());
            } catch (Exception e) {
                continue;
            }

            result.add(FriendResponseDto.builder()
                    .friendId(friend.getId())
                    .assetId(profile.getMyAvatar().getAvatar().getAssetId())
                    .enCryptUserId(encryptUserId)
                    .nickname(profile.getNickname())
                    .build());
        }

        result.sort(new Comparator<FriendResponseDto>() {
            @Override
            public int compare(FriendResponseDto o1, FriendResponseDto o2) {
                return o1.getNickname().compareToIgnoreCase(o2.getNickname());
            }
        });
        return result;
    }

    @Override
    public List<FriendResponseDto> getFollowerList(User user) {
        if(user == null) {
            return null;
        }

        List<Friend> friendList = friendRepository.findAllByFollowing(user);
        List<FriendResponseDto> result = new ArrayList<>();
        for(Friend friend : friendList) {
            Profile profile = profileRepository.findByUser(friend.getFollower());
            if(profile == null) {
                continue;
            }

            String encryptUserId = null;
            try {
                encryptUserId = Encrypt.encryptAES256(encryptProperties.getEncryptKey(), encryptProperties.getEncryptIv(), profile.getUser().getId().toString());
            } catch (Exception e) {
                continue;
            }

            result.add(FriendResponseDto.builder()
                    .friendId(friend.getId())
                    .assetId(profile.getMyAvatar().getAvatar().getAssetId())
                    .enCryptUserId(encryptUserId)
                    .nickname(profile.getNickname())
                    .build());
        }

        result.sort(new Comparator<FriendResponseDto>() {
            @Override
            public int compare(FriendResponseDto o1, FriendResponseDto o2) {
                return o1.getNickname().compareToIgnoreCase(o2.getNickname());
            }
        });
        return result;
    }
}
