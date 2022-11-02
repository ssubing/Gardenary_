package com.gardenary.domain.friend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendResponseDto {
    private String enCryptUserId;
    private String assetId;
    private String nickname;
    private Integer friendId;
}
