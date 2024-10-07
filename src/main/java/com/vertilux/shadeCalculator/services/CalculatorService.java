package com.vertilux.shadeCalculator.services;
import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.FabricCollection;
import com.vertilux.shadeCalculator.models.rollerShade.RollerShadeSystem;
import com.vertilux.shadeCalculator.models.rollerShade.RollerTube;
import com.vertilux.shadeCalculator.repositories.RollerFabricRepo;
import com.vertilux.shadeCalculator.repositories.RollerShadeRepo;
import com.vertilux.shadeCalculator.repositories.RollerTubeRepo;
import com.vertilux.shadeCalculator.schemas.*;
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
    private final RollerShadeRepo rollerShadeRepo;
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
     * This method takes in a shade proposal and returns the system limits
     * @param shadeProposal The schema with the necessary data to calculate the system limits
     * @return Response object with the system limits
     */
    public Response getSystemLimit(String unit, SystemLimitRequest shadeProposal) {
        RollerShadeSystem system = rollerShadeRepo.findByName(shadeProposal.getSystemName()).orElse(null);
        FabricCollection fabric = rollerFabricRepo.findByName(shadeProposal.getFabricName()).orElse(null);
        if (fabric == null || system == null) {
            return Response.builder()
                    .errors(List.of("Fabric, tube or system not found"))
                    .build();
        }

        List<SystemLimit> systemLimits = shadeCalculator.getAllSystemLimits(
                unit,
                system,
                fabric
        );

        SystemLimitResponse response = SystemLimitResponse.builder()
                .systemName(system.getName())
                .fabric(RollerFabricResponse.getRollerFabricResponse(fabric))
                .limits(systemLimits)
                .build();

        return Response.builder()
                .data(mapToJson(response))
                .build();
    }

    /**
     * This method returns the deflection of a shade.
     * @param template The schema with the necessary data to calculate the deflection
     * @return Response object with the deflection of the shade
     */
    public Response getTubeDeflection(ShadeTemplate template) {
        FabricCollection fabric = rollerFabricRepo.findByName(template.getFabricId()).orElse(null);
        RollerTube tube = rollerTubeRepo.findById(template.getTubeId()).orElse(null);

        if (fabric == null || tube == null) {
            return Response.builder()
                    .errors(List.of("Fabric, tube or system not found"))
                    .build();
        }

        Measurement deflection = shadeCalculator.getTubeDeflection(fabric, tube, template.getWidth(), template.getDrop());

        return Response.builder()
                .data(mapToJson(deflection))
                .build();
    }

}
