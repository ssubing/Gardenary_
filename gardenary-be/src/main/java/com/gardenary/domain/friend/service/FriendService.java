package com.gardenary.domain.friend.service;

import com.gardenary.domain.friend.dto.response.FriendResponseDto;
import com.gardenary.domain.user.entity.User;

public interface FriendService {

    boolean createFriend(User user, String encryptUserId);

    boolean deleteFriend(User user, int friendId);

    FriendResponseDto findUser(User user, String nickname);

}
