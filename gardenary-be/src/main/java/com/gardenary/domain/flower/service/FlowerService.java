package com.gardenary.domain.flower.service;

import com.gardenary.domain.flower.dto.*;
import com.gardenary.domain.flower.dto.response.AnswerCompleteResponseDto;
import com.gardenary.domain.flower.dto.response.FlowerListResponseDto;
import com.gardenary.domain.flower.dto.response.MyFlowerOnlyIdResponseDto;
import com.gardenary.domain.flower.dto.response.QuestionAnswerListResponseDto;
import com.gardenary.domain.user.entity.User;

public interface FlowerService {
    AnswerCompleteResponseDto createAnswer (User user, QuestionAnswerDto questionAnswerDto);
    QuestionAnswerListResponseDto getOneFlowerAnswerList (User user, int myFlowerId);
    QuestionAnswerListResponseDto getAllFlowerAnswerList (User user);
    MyFlowerOnlyIdResponseDto createNewFlower(User user);
    FlowerListResponseDto getFlowerList(User user);
}
