package com.gardenary.domain.flower.response;

import com.gardenary.domain.flower.dto.QuestionAnswerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerResponseListDto {
    private List<QuestionAnswerResponseDto> questionAnswerResponseDtoList;
}
