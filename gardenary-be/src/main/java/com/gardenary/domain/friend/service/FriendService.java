package com.gardenary.domain.friend.service;

import com.gardenary.domain.user.entity.User;

public interface FriendService {

    boolean createFriend(User user, User following);

    boolean deleteFriend(User user, User following);
}
