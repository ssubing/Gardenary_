package com.gardenary.domain.item.repository;

import com.gardenary.domain.item.entity.MyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyItemRepository extends JpaRepository<MyItem, Integer> {
}
