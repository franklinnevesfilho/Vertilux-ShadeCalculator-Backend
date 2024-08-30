package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.RollerShadeSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollerShadeRepo extends JpaRepository<RollerShadeSystem, String> {}
