package com.gardenary.domain.current.mapper;

import com.gardenary.domain.current.dto.GrowingPlantDto;
import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GrowingPlantMapper extends EntityMapper<GrowingPlantDto, GrowingPlant> {
    GrowingPlantMapper mapper = Mappers.getMapper(GrowingPlantMapper.class);

    @Override
    @Mapping(source = "myTree.id", target = "myTreeId")
    @Mapping(source = "myFlower.id", target = "myFlowerId")
    @Mapping(target = "userId", ignore = true)
    GrowingPlantDto toDto(final GrowingPlant entity);

    @Override
    @Mapping(source = "myTreeId", target = "myTree.id")
    @Mapping(source = "myFlowerId", target = "myFlower.id")
    @Mapping(source = "userId", target = "user.id")
    GrowingPlant toEntity (final GrowingPlantDto dto);
}
