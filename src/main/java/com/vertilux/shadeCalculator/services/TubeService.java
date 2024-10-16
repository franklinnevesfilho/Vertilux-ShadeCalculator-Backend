package com.vertilux.shadeCalculator.services;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.rollerShade.Tube;
import com.vertilux.shadeCalculator.repositories.RollerTubeRepo;
import com.vertilux.shadeCalculator.schemas.RollerTubeCreation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * RollerTubeService
 * This class is responsible for handling the business logic for the RollerTube entity.
 * @see Tube
 * @author Franklin Neves Filho
 */

@Slf4j
@AllArgsConstructor
@Service
public class TubeService extends MainService{
    private final RollerTubeRepo rollerTubeRepo;

    /**
     * This method returns all the roller tubes in the database.
     * @return Response object with all the roller tubes
     */
    public Response getAllRollerTubes(){
        List<Tube> tubes = rollerTubeRepo.findAll();

        return Response.builder()
                .data(mapToJson(tubes))
                .build();
    }

    /**
     * This method returns a roller tube by its name.
     * @param tubeName The name of the tube to be found
     * @return Response object with the found RollerTube
     */
    public Response getRollerTubeByName(String tubeName) {
        Tube found = rollerTubeRepo.findByName(tubeName).orElse(null);
        if (found != null) {
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Tube not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method returns a roller tube by its id.
     * @param tubeId The id of the tube to be found
     * @return Response object with the found RollerTube
     */
    public Response getRollerTubeById(String tubeId) {
        Optional<Tube> found = rollerTubeRepo.findById(tubeId);
        if (found.isPresent()) {
            return Response.builder()
                    .data(mapToJson(found.get()))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Tube not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method creates a new roller tube in the database.
     * @param tube The RollerTube object to be created
     * @return Response object with the created RollerTube
     */
    public Response createRollerTube(RollerTubeCreation tube) {
        Optional<Tube> found = rollerTubeRepo.findByName(tube.getName());
        if (found.isPresent()) {
            return Response.builder()
                    .errors(List.of("Tube already exists"))
                    .status("error")
                    .build();
        }else{
            Tube created = rollerTubeRepo.save(convertToRollerTube(tube));
            return Response.builder()
                    .data(mapToJson(created))
                    .build();
        }
    }

    /**
     * This method updates a roller tube in the database.
     * @param id The id of the RollerTube object to be updated
     * @param tube The RollerTube object to be updated
     * @return Response object with the updated RollerTube
     */
    public Response updateRollerTube(String id, RollerTubeCreation tube) {
        Tube found = rollerTubeRepo.findById(id).orElse(null);
        if (found != null) {
            Tube updated = rollerTubeRepo.save(convertToRollerTube(tube));
            return Response.builder()
                    .data(mapToJson(updated))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Tube not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method deletes a roller tube from the database.
     * @param tubeName The name of the tube to be deleted
     * @return Response object with the deleted RollerTube
     */
    public Response deleteRollerTube(String tubeName) {
        Tube found = rollerTubeRepo.findByName(tubeName).orElse(null);
        if (found != null) {
            rollerTubeRepo.delete(found);
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Tube not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method converts a RollerTubeCreation object to a RollerTube object.
     * @param tube The RollerTubeCreation object to be converted
     * @return The converted RollerTube object
     */
    private Tube convertToRollerTube(RollerTubeCreation tube) {

        return Tube.builder()
                .name(tube.getName())
                .outerDiameter(tube.getOuterDiameter())
                .innerDiameter(tube.getInnerDiameter())
                .build();
    }

}
