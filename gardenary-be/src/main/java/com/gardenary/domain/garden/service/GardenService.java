package com.gardenary.domain.garden.service;

import com.gardenary.domain.garden.dto.GardenDto;
import com.gardenary.domain.garden.dto.GardenUserIdDto;
import com.gardenary.domain.garden.dto.response.GardenListResponseDto;
import com.gardenary.domain.user.entity.User;

import java.util.List;

public interface GardenService {
    GardenListResponseDto getGardenInfo(GardenUserIdDto gardenUserIdDto);
    boolean modifyGarden(User user, List<GardenDto> gardenDtoList);
}
