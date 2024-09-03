package com.vertilux.shadeCalculator.utils;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.measurements.MeasurementUnit;
import com.vertilux.shadeCalculator.repositories.MeasurementRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for basic calculations for a roller shade
 * Methods:
 * - getRollUp calculates the roll up diameter of a fabric based on the drop and the tube
 *
 */

@Builder
@AllArgsConstructor
@Component
public class ShadeCalculator {
    private MeasurementConverter measurementConverter;
    private MeasurementRepo measurementRepo;

    /**
     * This method calculates the roll up of a shade.
     * @param drop the drop of the shade
     * @param tubeOuterDiameter the outer diameter of the tube
     * @param fabricThickness the thickness of the fabric
     * @return the roll up of the shade in the same unit as the drop
     */
    public Measurement getRollUp(Measurement drop, Measurement tubeOuterDiameter, Measurement fabricThickness){
        double rollUpValue = -1;

        MeasurementUnit unit = measurementRepo.findByUnit(drop.getUnit()).orElse(null);

        if (unit != null){
            tubeOuterDiameter = measurementConverter.convert(tubeOuterDiameter, unit);
            fabricThickness = measurementConverter.convert(fabricThickness, unit);

            rollUpValue = Math.sqrt(
                    Math.pow(tubeOuterDiameter.getValue(), 2) +
                            (4 * fabricThickness.getValue() * drop.getValue())/ Math.PI);
        }

        return Measurement.builder()
                .value(rollUpValue)
                .unit(unit != null? unit.toString() : "")
                .build();
    }

}
