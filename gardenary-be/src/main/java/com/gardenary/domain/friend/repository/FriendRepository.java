package com.gardenary.domain.friend.repository;

import com.gardenary.domain.friend.entity.Friend;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    Optional<Friend> findById(int id);
    Optional<Friend> findByFollowingAndFollower(User following, User follower);
    List<Friend> findAllByFollowing(User user);
    List<Friend> findAllByFollower(User user);
}
