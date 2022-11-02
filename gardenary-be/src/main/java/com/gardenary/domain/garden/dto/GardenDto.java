package com.gardenary.domain.garden.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GardenDto {
    private int id;
    private UUID userId;
    private int objectId;
    private double x;
    private double z;
    private int type;

}
