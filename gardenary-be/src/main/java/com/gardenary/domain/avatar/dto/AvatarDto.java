package com.gardenary.domain.avatar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvatarDto {

    private int id;

    private String assetId;

    private String requirement;
}
