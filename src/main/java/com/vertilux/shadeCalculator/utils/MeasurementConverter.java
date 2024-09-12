package com.vertilux.shadeCalculator.utils;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.measurements.MeasurementUnit;
import com.vertilux.shadeCalculator.models.measurements.UnitConversion;
import com.vertilux.shadeCalculator.repositories.ConversionRepo;
import com.vertilux.shadeCalculator.repositories.UnitRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for converting measurements
 * from one unit to another.
 * It uses a list of UnitConversions to perform the conversions.
 * If a conversion is not found, it returns -1.
 */

@Slf4j
@AllArgsConstructor
@Component
public class MeasurementConverter {
    private UnitRepo unitRepo;
    private ConversionRepo conversionRepo;

    /**
     * @param from the measurement to convert
     * @param to the unit to convert to
     * @return the converted value, or -1 if the conversion is not possible
     */
    public Measurement convert(Measurement from, String to) {
        Measurement result = Measurement.builder().value(-1).build();
        // find to unit
        MeasurementUnit toUnit = unitRepo.findByUnit(to).orElse(null);
        MeasurementUnit fromUnit = unitRepo.findByUnit(from.getUnit()).orElse(null);
        if(toUnit != null && fromUnit != null){
            if (!fromUnit.equals(toUnit)) {
                UnitConversion conversion = conversionRepo.findByFromAndTo(fromUnit, toUnit).orElse(null);
                if (conversion != null) {
                    double value = from.getValue() * conversion.getFactor();
                    result = Measurement.builder()
                            .value(value)
                            .unit(to)
                            .build();
                }
            } else {
                result = from;
            }
        }
        return result;
    }
}
