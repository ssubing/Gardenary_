package com.gardenary.domain.flower.service;

import com.gardenary.domain.flower.dto.AnswerCompleteDto;
import com.gardenary.domain.flower.dto.QuestionAnswerListDto;
import com.gardenary.domain.flower.dto.QuestionAnswerDto;
import com.gardenary.domain.user.entity.User;

public interface FlowerService {
    AnswerCompleteDto createAnswer (User user, QuestionAnswerDto questionAnswerDto);
    QuestionAnswerListDto getOneFlowerAnswerList (User user, int myFlowerId);
    QuestionAnswerListDto getAllFlowerAnswerList (User user);
}
