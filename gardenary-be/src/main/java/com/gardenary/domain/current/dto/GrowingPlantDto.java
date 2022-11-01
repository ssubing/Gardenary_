package com.gardenary.domain.current.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowingPlantDto {
    private int id;
    private int myFlowerId;
    private int myTreeId;
    private UUID userId;
    private int answerDays;
    private int diaryDays;
    private int answerCnt;
}
