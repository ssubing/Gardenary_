package com.gardenary.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "const")
public class ConstProperties {
    private final int treeSize;
    private final int flowerSize;
    private final int questionSize;
    private final int expTree;
    private final int expFlower;
    private final int expLevelup;
    private final int nicknameSize;
    private final int contentSize;
}
