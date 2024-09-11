package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.BottomRail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShadeProposal implements Schema{
    private String fabricName;
    private String tubeName;
    private Measurement width;
    private Measurement drop;
    private BottomRail bottomRail;
}
