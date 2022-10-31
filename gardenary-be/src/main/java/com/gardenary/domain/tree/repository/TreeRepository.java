package com.gardenary.domain.tree.repository;

import com.gardenary.domain.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree, Integer> {
    Optional<Tree> findById(int id);
}
