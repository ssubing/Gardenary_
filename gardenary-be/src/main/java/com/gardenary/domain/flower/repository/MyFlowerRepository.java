package com.gardenary.domain.flower.repository;

import com.gardenary.domain.flower.entity.MyFlower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyFlowerRepository extends JpaRepository<MyFlower, Integer> {
}
