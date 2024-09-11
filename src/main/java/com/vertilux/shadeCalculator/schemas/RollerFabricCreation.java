package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>RollerFabricCreation</h1>
 *
 * This class represents the schema needed to create a RollerFabric.
 *
 * @see com.vertilux.shadeCalculator.models.rollerShade.RollerFabric
 * @author Franklin Neves Filho
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RollerFabricCreation implements Schema{
    private String name;
    private Measurement thickness;
    private Measurement weight;

}
