package com.gardenary.domain.garden.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GardenTreeResponseDto {

    private LocalDateTime start;
    private LocalDateTime end;
    private double x;
    private double z;
    private int myTreeId;
    private String assetId;
}
