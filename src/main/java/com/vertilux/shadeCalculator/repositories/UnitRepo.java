package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.measurements.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MeasurementRepo
 * This repository interface is responsible for handling the database operations for the MeasurementUnit entity.
 * @author Franklin Neves Filho
 */

@Repository
public interface UnitRepo extends JpaRepository<MeasurementUnit, String> {
    Optional<MeasurementUnit> findByUnit(String unit);
}
