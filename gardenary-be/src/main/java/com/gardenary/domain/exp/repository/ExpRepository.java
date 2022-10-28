package com.gardenary.domain.exp.repository;

import com.gardenary.domain.exp.entity.Exp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpRepository extends JpaRepository<Exp, Integer> {
}
