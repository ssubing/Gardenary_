package com.gardenary.domain.tree.repository;

import com.gardenary.domain.tree.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    Optional<Diary> findById(int id);
}
