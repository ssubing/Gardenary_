package com.gardenary.domain.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDto {
    private List<InventoryTreeResponseDto> trees;
    private List<InventoryFlowerResponseDto> flowers;
    private List<InventoryItemResponseDto> items;
}
