package com.gardenary.domain.garden.service;

import com.gardenary.domain.garden.response.GardenListResponseDto;

public interface GardenService {
    GardenListResponseDto getGardenInfo(String userId);
}
