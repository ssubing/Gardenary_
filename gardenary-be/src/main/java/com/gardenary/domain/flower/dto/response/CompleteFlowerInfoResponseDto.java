package com.gardenary.domain.flower.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteFlowerInfoResponseDto {
    private String name;
    private String assetId;
    private String color;
}
