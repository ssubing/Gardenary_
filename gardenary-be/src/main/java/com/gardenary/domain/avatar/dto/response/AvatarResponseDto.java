package com.gardenary.domain.avatar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvatarResponseDto {
    private String assetId;
    private boolean isAcquired;
    private String requirement;
}
