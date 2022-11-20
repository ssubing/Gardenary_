package com.gardenary.domain.friend.mapper;

import com.gardenary.domain.friend.dto.FriendDto;
import com.gardenary.domain.friend.entity.Friend;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendMapper extends EntityMapper<FriendDto, Friend> {
    FriendMapper mapper = Mappers.getMapper(FriendMapper.class);

    @Override
    @Mapping(source = "following.id", target = "followingId")
    @Mapping(source = "follower.id", target = "followerId")
    FriendDto toDto(final Friend entity);

    @Override
    @Mapping(source = "followingId", target = "following.id")
    @Mapping(source = "followerId", target = "follower.id")
    Friend toEntity(final FriendDto dto);
}
