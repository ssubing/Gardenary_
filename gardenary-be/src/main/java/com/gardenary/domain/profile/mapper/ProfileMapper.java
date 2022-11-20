package com.gardenary.domain.profile.mapper;

import com.gardenary.domain.profile.dto.ProfileDto;
import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface ProfileMapper extends EntityMapper<ProfileDto, Profile> {
    ProfileMapper mapper = Mappers.getMapper(ProfileMapper.class);

    @Override
    @Mapping(source = "MyAvatar.id", target = "myAvatarId")
    @Mapping(target = "userId", ignore = true)
    ProfileDto toDto(final Profile entity);

    @Override
    @Mapping(source = "myAvatarId", target = "MyAvatar.id")
    @Mapping(source = "userId", target = "user.id")
    Profile toEntity (final ProfileDto dto);
}
