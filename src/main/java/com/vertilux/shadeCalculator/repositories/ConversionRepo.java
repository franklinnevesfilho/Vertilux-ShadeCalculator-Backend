package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.measurements.MeasurementUnit;
import com.vertilux.shadeCalculator.models.measurements.UnitConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ConversionRepo
 * This repository interface is responsible for handling the database operations for the UnitConversion entity.
 *
 * @see UnitConversion
 * @see MeasurementRepo
 * @author Franklin Neves Filho
 */
@Repository
public interface ConversionRepo extends JpaRepository<UnitConversion, String> {
    Optional<UnitConversion> findByFromUnitAndToUnit(MeasurementUnit fromUnit, MeasurementUnit toUnit);
}
