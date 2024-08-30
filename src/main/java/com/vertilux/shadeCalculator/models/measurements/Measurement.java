package com.vertilux.shadeCalculator.models.measurements;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Measurement
 * containing a value and a unit.
 *
 * @author Franklin Neves Filho
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class Measurement {
    public double value;
    public String unit;
}
