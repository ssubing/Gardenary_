package com.gardenary.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {

    private int id;

    private int myAvatarId;

    private UUID userId;

    private String nickname;

    private String requirement;

}
