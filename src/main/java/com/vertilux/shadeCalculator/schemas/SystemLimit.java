package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SystemLimit {
    private RollerTubeResponse tube;
    private Measurement maxDrop;
    private Measurement maxWidth;
    private Measurement deflection;
}
