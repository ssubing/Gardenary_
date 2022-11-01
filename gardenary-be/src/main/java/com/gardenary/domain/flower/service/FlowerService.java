package com.gardenary.domain.flower.service;

import com.gardenary.domain.flower.dto.*;
import com.gardenary.domain.user.entity.User;

public interface FlowerService {
    AnswerCompleteDto createAnswer (User user, QuestionAnswerDto questionAnswerDto);
    QuestionAnswerListDto getOneFlowerAnswerList (User user, int myFlowerId);
    QuestionAnswerListDto getAllFlowerAnswerList (User user);
    MyFlowerOnlyIdDto createNewFlower(User user);
}
