package com.gardenary.domain.tree.service;

import com.gardenary.domain.user.entity.User;

public interface TreeService {

    boolean createMyTree(User user);

    boolean updateCurTree(User user);

    int checkTreeExp(User user);
}
