package com.gardenary.domain.auth.service;


import com.gardenary.domain.auth.dto.response.AuthResponseDto;
import com.gardenary.domain.user.entity.User;

public interface AuthService {

    AuthResponseDto signIn(User user);

}
