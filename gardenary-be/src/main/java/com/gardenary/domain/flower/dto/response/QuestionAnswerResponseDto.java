package com.gardenary.domain.flower.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerResponseDto {
    private String question;
    private String content;
    private LocalDateTime createdAt;
    private int questionNum;
}
