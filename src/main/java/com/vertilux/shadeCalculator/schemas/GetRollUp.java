package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GetRollUp
 * This class is a schema to get the roll up of a shade.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetRollUp implements Schema{
    private Measurement drop;
    private Measurement tubeOuterDiameter;
    private Measurement fabricThickness;
}
