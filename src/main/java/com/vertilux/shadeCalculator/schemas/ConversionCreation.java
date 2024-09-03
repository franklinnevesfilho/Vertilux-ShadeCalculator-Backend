package com.vertilux.shadeCalculator.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>MeasurementUnitCreation</h1>
 *
 * This class represents the schema needed to create a MeasurementUnit.
 *
 * @author Franklin Neves Filho
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConversionCreation implements Schema {
    private String from;
    private String to;
    private double rate;
}
