package com.gardenary.domain.flower.repository;

import com.gardenary.domain.flower.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
