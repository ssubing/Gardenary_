package com.gardenary.domain.item.dto;

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
public class MyItemDto {

    private int id;

    private int itemId;

    private UUID userId;

    private LocalDateTime createdAt;

}
