package com.gardenary.domain.tree.repository;

import com.gardenary.domain.tree.entity.Diary;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    Optional<Diary> findById(int id);
    List<Diary> findAllByMyTree_UserAndDiaryDate(User user, LocalDateTime date);
    List<Diary> findAllByMyTree(MyTree myTree, Sort sort);
    Optional<Diary> findTop1ByMyTree_UserOrderByCreatedAtDesc(User user);
}
