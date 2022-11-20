package com.gardenary.domain.tree.mapper;

import com.gardenary.domain.tree.dto.DiaryDto;
import com.gardenary.domain.tree.dto.MyTreeDto;
import com.gardenary.domain.tree.entity.Diary;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiaryMapper extends EntityMapper<DiaryDto, Diary> {
    DiaryMapper mapper = Mappers.getMapper(DiaryMapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    @Mapping(source = "myTree.tree.id", target = "treeId")
    @Mapping(source = "myTree.id", target = "myTreeId")
    DiaryDto toDto(final Diary entity);

    @Override
    @Mapping(source = "treeId", target = "myTree.tree.id")
    @Mapping(source = "myTreeId", target = "myTree.id")
    Diary toEntity(final DiaryDto dto);
}
