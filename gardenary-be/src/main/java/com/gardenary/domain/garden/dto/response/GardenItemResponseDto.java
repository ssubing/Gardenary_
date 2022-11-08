package com.gardenary.domain.garden.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GardenItemResponseDto {

    private double x;
    private double z;
    private int myItemId;
    private String assetId;
}
