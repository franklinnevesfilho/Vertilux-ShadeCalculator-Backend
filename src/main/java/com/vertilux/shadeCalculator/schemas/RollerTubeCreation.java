package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.Tube;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>RollerTubeCreation</h1>
 *
 * This class represents the schema needed to create a RollerTube.
 *
 * @see Tube
 *
 * @author Franklin Neves Filho
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RollerTubeCreation {
    private String name;
    private Measurement outerDiameter;
    private Measurement innerDiameter;
}
