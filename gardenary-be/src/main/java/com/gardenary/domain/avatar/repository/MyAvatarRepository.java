package com.gardenary.domain.avatar.repository;

import com.gardenary.domain.avatar.entity.MyAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyAvatarRepository extends JpaRepository<MyAvatar, Integer> {
}
