package com.gardenary.domain.flower.service;

import com.gardenary.domain.flower.dto.*;
import com.gardenary.domain.flower.response.AnswerCompleteDto;
import com.gardenary.domain.flower.response.MyFlowerOnlyIdDto;
import com.gardenary.domain.flower.response.QuestionAnswerResponseListDto;
import com.gardenary.domain.user.entity.User;

public interface FlowerService {
    AnswerCompleteDto createAnswer (User user, QuestionAnswerDto questionAnswerDto);
    QuestionAnswerResponseListDto getOneFlowerAnswerList (User user, int myFlowerId);
    QuestionAnswerResponseListDto getAllFlowerAnswerList (User user);
    MyFlowerOnlyIdDto createNewFlower(User user);
}
