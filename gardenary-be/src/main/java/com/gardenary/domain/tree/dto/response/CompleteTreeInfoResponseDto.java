package com.gardenary.domain.tree.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteTreeInfoResponseDto {
    private String name;
    private String assetId;
}
