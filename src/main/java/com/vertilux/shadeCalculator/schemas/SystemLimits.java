package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SystemLimits {
    private Measurement width;
    private Measurement drop;
    private Measurement deflection;
}
