package com.gardenary.domain.item.repository;

import com.gardenary.domain.item.entity.MyItem;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyItemRepository extends JpaRepository<MyItem, Integer> {
    List<MyItem> findAllByUser(User user);
}
