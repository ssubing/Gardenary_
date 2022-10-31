package com.gardenary.domain.tree.repository;

import com.gardenary.domain.tree.entity.MyTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyTreeRepository extends JpaRepository<MyTree, Integer> {
    Optional<MyTree> findById(int id);
}
