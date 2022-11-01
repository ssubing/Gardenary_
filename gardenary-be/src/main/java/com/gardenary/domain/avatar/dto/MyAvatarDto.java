package com.gardenary.domain.avatar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyAvatarDto {

    private int id;

    private int avatarId;

    private UUID userId;

    private LocalDateTime createdAt;

}
