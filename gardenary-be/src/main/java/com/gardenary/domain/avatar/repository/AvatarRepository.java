package com.gardenary.domain.avatar.repository;

import com.gardenary.domain.avatar.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
}
