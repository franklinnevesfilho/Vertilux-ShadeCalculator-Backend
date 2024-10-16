package com.vertilux.shadeCalculator.schemas;


import com.vertilux.shadeCalculator.models.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <h1>RollerShadeSystemCreation</h1>
 *
 * This class represents the schema needed to create a RollerShadeSystem.
 *
 * @author Franklin Neves Filho
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RollerShadeSystemCreation implements Schema {
    private String name;
    private Measurement maxDiameter;
}
