package com.gardenary.domain.flower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerDto {
    private int id;
    private int questionId;
    private int myFlowerId;
    private int flowerId;
    private UUID userId;
    private LocalDateTime createdAt;
    private String content;
    private boolean isOver;
    private int questionNum;

}
