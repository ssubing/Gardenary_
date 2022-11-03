package com.gardenary.domain.item.service;

import com.gardenary.domain.item.dto.response.ItemResponseDto;
import com.gardenary.domain.user.entity.User;

public interface ItemService {
    ItemResponseDto createItem(User user);
}
