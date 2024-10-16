package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.UnitConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ConversionRepo
 * This repository interface is responsible for handling the database operations for the UnitConversion entity.
 *
 * @see UnitConversion
 * @see UnitRepo
 * @author Franklin Neves Filho
 */
@Repository
public interface ConversionRepo extends JpaRepository<UnitConversion, String> {
    Optional<UnitConversion> findByFromAndTo(MeasurementUnit fromUnit, MeasurementUnit toUnit);
}
