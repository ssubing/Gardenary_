package com.gardenary.domain.garden.mapper;

import com.gardenary.domain.garden.dto.GardenDto;
import com.gardenary.domain.garden.entity.Garden;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GardenMapper extends EntityMapper<GardenDto, Garden> {

    @Override
    @Mapping(target = "userId", ignore = true)
    GardenDto toDto(final Garden entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    Garden toEntity(final GardenDto dto);

}
