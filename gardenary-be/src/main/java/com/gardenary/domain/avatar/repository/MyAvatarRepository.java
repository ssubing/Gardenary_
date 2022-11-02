package com.gardenary.domain.avatar.repository;

import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyAvatarRepository extends JpaRepository<MyAvatar, Integer> {
    Optional<MyAvatar> findByUser(User user);
}
