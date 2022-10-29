package com.gardenary.domain.friend.repository;

import com.gardenary.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    Optional<Friend> findById(int id);
}
