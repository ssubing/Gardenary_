package com.gardenary.domain.profile.repository;

import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByUser(User user);

    Optional<Profile> findByNickname(String nickname);
}
