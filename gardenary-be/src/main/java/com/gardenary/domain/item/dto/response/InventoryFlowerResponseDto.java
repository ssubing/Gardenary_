package com.gardenary.domain.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryFlowerResponseDto {
    private String name;
    private String period;
    private String assetId;
    private boolean used;
    private int objectId;
}
