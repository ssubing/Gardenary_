package com.gardenary.domain.tree.repository;

import com.gardenary.domain.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree, Integer> {
    List<Tree> findAll();
}
