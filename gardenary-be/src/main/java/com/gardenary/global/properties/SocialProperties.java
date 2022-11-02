package com.gardenary.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "social")
public class SocialProperties {
    private final String kakaoClientId;
    private final String kakaoClientSecret;
    private final String kakaoApiUrl;
}
