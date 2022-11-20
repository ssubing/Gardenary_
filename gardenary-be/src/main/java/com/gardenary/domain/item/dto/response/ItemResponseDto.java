package com.gardenary.domain.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDto {
    private String itemAssetId;
    private String itemName;
    private boolean newItem;
}
