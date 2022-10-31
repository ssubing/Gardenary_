package com.gardenary.domain.flower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerListDto {
    private List<QuestionAnswerDto> questionAnswerDtoList;
}
