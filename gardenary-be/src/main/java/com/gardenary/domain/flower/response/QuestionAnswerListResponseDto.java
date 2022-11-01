package com.gardenary.domain.flower.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerListResponseDto {
    private List<QuestionAnswerResponseDto> questionAnswerResponseDtoList;
}
