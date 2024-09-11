package com.vertilux.shadeCalculator.services;
import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.RollerFabric;
import com.vertilux.shadeCalculator.models.rollerShade.RollerTube;
import com.vertilux.shadeCalculator.repositories.RollerFabricRepo;
import com.vertilux.shadeCalculator.repositories.RollerTubeRepo;
import com.vertilux.shadeCalculator.schemas.GetRollUp;
import com.vertilux.shadeCalculator.schemas.ShadeProposal;
import com.vertilux.shadeCalculator.utils.ShadeCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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
    private final RollerFabricRepo rollerFabricRepo;
    private final RollerTubeRepo rollerTubeRepo;

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

    /**
     * This method returns the tube deflection based on measurements
     * @param shadeProposal The schema with the necessary data to calculate the tube deflection
     * @return Response object with the tube deflection
     */
    public Response getTubeDeflection(ShadeProposal shadeProposal) {

        RollerFabric fabric = rollerFabricRepo.findByName(shadeProposal.getFabricName()).orElse(null);
        RollerTube tube = rollerTubeRepo.findByName(shadeProposal.getTubeName()).orElse(null);

        if(fabric == null || tube == null){
            return Response.builder()
                    .errors(List.of("Fabric or tube not found"))
                    .build();
        }

        Measurement tubeDeflection = shadeCalculator.getTubeDeflection(
                fabric,
                tube,
                shadeProposal.getWidth(),
                shadeProposal.getDrop()
        );
        return Response.builder()
                .data(mapToJson(tubeDeflection))
                .build();
    }
}
