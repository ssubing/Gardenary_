package com.gardenary.domain.flower.repository;

import com.gardenary.domain.flower.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Integer> {
}
