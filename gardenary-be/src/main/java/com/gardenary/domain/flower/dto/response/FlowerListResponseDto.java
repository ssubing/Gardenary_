package com.gardenary.domain.flower.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowerListResponseDto {
    private List<FlowerResponseDto> flowerList;
}
