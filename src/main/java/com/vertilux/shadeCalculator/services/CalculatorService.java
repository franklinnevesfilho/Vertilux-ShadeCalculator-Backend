package com.vertilux.shadeCalculator.services;
import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.schemas.GetRollUp;
import com.vertilux.shadeCalculator.utils.ShadeCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Calculator Service
 * This class converts all responses from the ShadeCalculator class into a Response object
 * @see ShadeCalculator for precise usage
 * @author Franklin Neves Filho
 */

@Slf4j
@AllArgsConstructor
@Service
public class CalculatorService extends MainService{
    private final ShadeCalculator shadeCalculator;

    /**
     * This method returns the roll up of a shade.
     * @param getRollUp The schema with the necessary data to calculate the roll up
     * @return Response object with the roll up of the shade
     */
    public Response getRollUp(GetRollUp getRollUp){
        Measurement rollUp = shadeCalculator.getRollUp(
                getRollUp.getDrop(),
                getRollUp.getTubeOuterDiameter(),
                getRollUp.getFabricThickness()
        );
        return Response.builder()
                .data(mapToJson(rollUp))
                .build();
    }
}
