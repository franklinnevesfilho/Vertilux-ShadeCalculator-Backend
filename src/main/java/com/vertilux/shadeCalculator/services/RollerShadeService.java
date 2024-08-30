package com.vertilux.shadeCalculator.services;


import com.vertilux.shadeCalculator.models.RollerShadeSystem;
import com.vertilux.shadeCalculator.repositories.RollerShadeRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RollerShadeService {
    private final RollerShadeRepo rollerShadeRepo;

    public void save(RollerShadeSystem system) {
        rollerShadeRepo.save(system);
        log.info("Roller shade system saved successfully");
    }

    public List<RollerShadeSystem> getAll() {
        return rollerShadeRepo.findAll();
    }
}
