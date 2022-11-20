package com.gardenary.domain.flower.repository;

import com.gardenary.domain.flower.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {
    List<Flower> findAllByOrderByNameAscIdAsc();
    Flower findById(String flowerIdx);
}
