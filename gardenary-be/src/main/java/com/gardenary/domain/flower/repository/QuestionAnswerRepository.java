package com.gardenary.domain.flower.repository;

import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.entity.QuestionAnswer;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Integer> {
    List<QuestionAnswer> findAllByMyFlower_UserOrderByCreatedAtDesc(User user);
    List<QuestionAnswer> findAllByMyFlowerAndMyFlower_UserOrderByCreatedAtDesc(MyFlower myFlower, User user);
}
