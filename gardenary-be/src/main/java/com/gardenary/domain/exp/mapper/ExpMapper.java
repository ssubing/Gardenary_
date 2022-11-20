package com.gardenary.domain.exp.mapper;

import com.gardenary.domain.exp.dto.ExpDto;
import com.gardenary.domain.exp.entity.Exp;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpMapper extends EntityMapper<ExpDto, Exp> {

    ExpMapper mapper = Mappers.getMapper(ExpMapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    ExpDto toDto(final Exp entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    Exp toEntity(final ExpDto dto);
}
