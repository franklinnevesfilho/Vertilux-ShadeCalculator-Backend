package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepo extends JpaRepository<Measurement, String> {

}
