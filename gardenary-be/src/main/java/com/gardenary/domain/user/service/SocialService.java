package com.gardenary.domain.user.service;

import com.gardenary.domain.user.entity.User;

public interface SocialService {

    User checkSignUp(String kakaoId);

}
