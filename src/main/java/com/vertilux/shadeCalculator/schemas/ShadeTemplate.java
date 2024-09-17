package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShadeTemplate implements Schema{
    private String fabricId;
    private String tubeId;
    private Measurement width;
    private Measurement drop;
}
