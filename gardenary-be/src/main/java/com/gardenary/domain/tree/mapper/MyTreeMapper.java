package com.gardenary.domain.tree.mapper;

import com.gardenary.domain.tree.dto.MyTreeDto;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MyTreeMapper extends EntityMapper<MyTreeDto, MyTree> {
    MyTreeMapper mapper = Mappers.getMapper(MyTreeMapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    @Mapping(source = "tree.id", target = "treeId")
    MyTreeDto toDto(final MyTree entity);

    @Override
    @Mapping(source = "treeId", target = "tree.id")
    @Mapping(target = "user", ignore = true)
    MyTree toEntity(final MyTreeDto dto);
}
