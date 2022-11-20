package com.gardenary.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendDto {

    private int id;

    private UUID followingId;

    private UUID followerId;
}
