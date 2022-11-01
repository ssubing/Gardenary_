package com.gardenary.domain.friend.service;

import com.gardenary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //생성자 주입. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성. AutoWired 불필요
@Slf4j //로깅 어노테이션
public class FriendServiceImpl implements FriendService {

    @Override
    public boolean createFriend(User user, User following) {
        return false;
    }

    @Override
    public boolean deleteFriend(User user, User following) {
        return false;
    }
}
