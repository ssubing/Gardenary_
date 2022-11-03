package com.gardenary.domain.avatar.repository;

import com.gardenary.domain.avatar.entity.Avatar;
import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyAvatarRepository extends JpaRepository<MyAvatar, Integer> {
    Optional<MyAvatar> findByUser(User user);

    List<MyAvatar> findAllByUser(User user);

    Optional<MyAvatar> findByUserAndAvatar(User user, Avatar avatar);
}
