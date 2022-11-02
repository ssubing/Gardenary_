package com.gardenary.domain.tree.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeResponseDto {

    private String name;

    private String content;

    private String assetId;

    private boolean isAcquired;
}
