package com.gardenary.domain.item.mapper;

import com.gardenary.domain.item.dto.MyItemDto;
import com.gardenary.domain.item.entity.MyItem;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface MyItemMapper extends EntityMapper<MyItemDto, MyItem> {
    MyItemMapper mapper = Mappers.getMapper(MyItemMapper.class);

    @Override
    @Mapping(source = "Item.id", target = "itemId")
    @Mapping(target = "userId", ignore = true)
    MyItemDto toDto(final MyItem entity);

    @Override
    @Mapping(source = "itemId", target = "Item.id")
    @Mapping(source = "userId", target = "user.id")
    MyItem toEntity(final MyItemDto dto);
}
