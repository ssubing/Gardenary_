package com.gardenary.domain.tree.repository;

import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyTreeRepository extends JpaRepository<MyTree, Integer> {
    Optional<MyTree> findById(int id);

    List<MyTree> findAllByUser(User user);
}
