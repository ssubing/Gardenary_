package com.gardenary.domain.flower.repository;

import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyFlowerRepository extends JpaRepository<MyFlower, Integer> {
    List<MyFlower> findAllByUser(User user);
}
