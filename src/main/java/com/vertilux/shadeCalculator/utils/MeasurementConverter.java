package com.vertilux.shadeCalculator.utils;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.measurements.MeasurementUnit;
import com.vertilux.shadeCalculator.models.measurements.UnitConversion;
import com.vertilux.shadeCalculator.repositories.ConversionRepo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class is responsible for converting measurements
 * from one unit to another.
 * It uses a list of UnitConversions to perform the conversions.
 * If a conversion is not found, it returns -1.
 */
@Component
public class MeasurementConverter {
    private List<UnitConversion> conversions;

    public MeasurementConverter(ConversionRepo conversionRepo) {
        this.conversions = conversionRepo.findAll();
        conversions = conversionRepo.findAll();
    }

    /**
     * @param from the measurement to convert
     * @param to the unit to convert to
     * @return the converted value, or -1 if the conversion is not possible
     */
    public Measurement convert(Measurement from, MeasurementUnit to) {
        double result = -1;

        UnitConversion conversion = conversions.stream()
                .filter(c -> c.getFromUnit().toString().equals(from.getUnit()) && c.getToUnit().equals(to))
                .findFirst()
                .orElse(null);

        if (conversion != null) {
            result = from.value * conversion.getConversionFactor();
        }

        return Measurement.builder()
                .value(result)
                .unit(to.toString())
                .build();
    }
}
