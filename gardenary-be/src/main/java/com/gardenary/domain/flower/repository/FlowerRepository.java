package com.gardenary.domain.flower.repository;

import com.gardenary.domain.flower.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRepository extends JpaRepository<Flower, Long> {
}
