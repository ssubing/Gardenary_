package com.gardenary.domain.avatar.mapper;

import com.gardenary.domain.avatar.dto.MyAvatarDto;
import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface MyAvatarMapper extends EntityMapper<MyAvatarDto, MyAvatar> {
    MyAvatarMapper mapper = Mappers.getMapper(MyAvatarMapper.class);

    @Override
    @Mapping(source = "Avatar.id", target = "avatarId")
    @Mapping(target = "userId", ignore = true)
    MyAvatarDto toDto(final MyAvatar entity);

    @Override
    @Mapping(source = "avatarId", target = "Avatar.id")
    @Mapping(source = "userId", target = "user.id")
    MyAvatar toEntity (final MyAvatarDto dto);
}