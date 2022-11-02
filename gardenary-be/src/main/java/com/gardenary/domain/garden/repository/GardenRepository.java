package com.gardenary.domain.garden.repository;

import com.gardenary.domain.garden.entity.Garden;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GardenRepository extends JpaRepository<Garden, Integer> {

    List<Garden> findAllByUser(User user);
}
