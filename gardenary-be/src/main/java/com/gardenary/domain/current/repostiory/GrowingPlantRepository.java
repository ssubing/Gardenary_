package com.gardenary.domain.current.repostiory;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowingPlantRepository extends JpaRepository<GrowingPlant, Integer> {

    GrowingPlant findByUser (User user);
}
