package com.gardenary.domain.current.service;

import com.gardenary.domain.current.Response.GrowingPlantResponseDto;
import com.gardenary.domain.user.entity.User;

public interface CurrentService {

    GrowingPlantResponseDto getCurrentInfo(User user);
}
