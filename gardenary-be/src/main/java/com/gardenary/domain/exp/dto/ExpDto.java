package com.gardenary.domain.exp.dto;

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
public class ExpDto {
    private int id;
    private UUID userId;
    private LocalDateTime createdAt;
    private int diaryId;
    private boolean type;
    private int expAmount;

}
