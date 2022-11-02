package com.gardenary.domain.auth.service;


import com.gardenary.domain.auth.dto.response.AuthResponseDto;
import com.gardenary.domain.user.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthResponseDto signIn(User user);

    void signOut(HttpServletRequest req);

    AuthResponseDto refresh(String refreshToken);

}
