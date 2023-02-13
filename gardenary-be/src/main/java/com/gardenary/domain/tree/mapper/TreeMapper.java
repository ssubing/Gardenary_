package com.gardenary.domain.tree.mapper;

import com.gardenary.domain.tree.dto.TreeDto;
import com.gardenary.domain.tree.entity.Tree;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TreeMapper extends EntityMapper<TreeDto, Tree> {
    TreeMapper mapper = Mappers.getMapper(TreeMapper.class);

    @Override
    TreeDto toDto(final Tree entity);

    @Override
    Tree toEntity(final TreeDto dto);
}
