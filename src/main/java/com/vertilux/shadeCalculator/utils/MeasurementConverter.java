package com.vertilux.shadeCalculator.utils;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
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
    public Measurement convert(Measurement from, String to) {
        Measurement result = Measurement.builder().value(-1).build();

        if (!from.getUnit().equals(to)) {
            UnitConversion conversion = conversions.stream()
                    .filter(c -> c.getFrom().getUnit().equals(from.getUnit())
                            && c.getTo().getUnit().equals(to))
                    .findFirst()
                    .orElse(null);
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
        return result;
    }
}
