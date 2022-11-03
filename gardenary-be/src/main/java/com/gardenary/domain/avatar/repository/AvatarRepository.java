package com.gardenary.domain.avatar.repository;

import com.gardenary.domain.avatar.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
    Optional<Avatar> findByAssetId(String assetId);
}
