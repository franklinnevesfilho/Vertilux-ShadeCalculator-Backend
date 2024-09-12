package com.vertilux.shadeCalculator.services;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.RollerFabric;
import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.repositories.RollerFabricRepo;
import com.vertilux.shadeCalculator.schemas.RollerFabricCreation;
import com.vertilux.shadeCalculator.utils.MeasurementConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RollerFabricService
 * This class is responsible for handling the business logic for the RollerFabric entity.
 * @see RollerFabric
 * @author Franklin Neves Filho
 */

@Slf4j
@AllArgsConstructor
@Service
public class RollerFabricService extends MainService {
    private final RollerFabricRepo rollerFabricRepo;
    private final MeasurementConverter measurementConverter;

    /**
     * This method returns all the roller fabrics in the database.
     *
     * @return Response object with all the roller fabrics
     */
    public Response getAllRollerFabrics() {
        List<RollerFabric> rollerFabrics = rollerFabricRepo.findAll();

        return Response.builder()
                .data(mapToJson(rollerFabrics))
                .build();
    }

    /**
     * This method returns a roller fabric by its name.
     *
     * @param fabricName The name of the fabric to be found
     * @return Response object with the found RollerFabric
     */
    public Response getRollerFabricByName(String fabricName) {
        RollerFabric found = rollerFabricRepo.findByName(fabricName).orElse(null);
        if (found != null) {
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Fabric not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method returns a roller fabric by its id.
     *
     * @param fabricId The id of the fabric to be found
     * @return Response object with the found RollerFabric
     */
    public Response getRollerFabricById(String fabricId) {
        RollerFabric found = rollerFabricRepo.findById(fabricId).orElse(null);
        if (found != null) {
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Fabric not found"))
                    .status("error")
                    .build();
        }

    }

    /**
     * This method creates a new roller fabric in the database.
     *
     * @param fabric The fabric to be created
     * @return Response object with the created RollerFabric
     */
    public Response createRollerFabric(RollerFabricCreation fabric) {
        RollerFabric found = rollerFabricRepo.findByName(fabric.getName()).orElse(null);

        if (found != null) {
            return Response.builder()
                    .errors(List.of("Fabric already exists"))
                    .status("error")
                    .build();
        } else {

            RollerFabric created = rollerFabricRepo.save(RollerFabric.builder()
                    .name(fabric.getName())
                    .thickness(fabric.getThickness())
                    .weight(fabric.getWeight())
                    .build());

            return Response.builder()
                    .data(mapToJson(created))
                    .build();
        }

    }

    /**
     * This method updates a roller fabric in the database.
     *
     * @param id     The id of the fabric to be updated
     * @param fabric The fabric to be updated
     * @return Response object with the updated RollerFabric
     */
    public Response updateRollerFabric(String id, RollerFabricCreation fabric) {
        RollerFabric found = rollerFabricRepo.findById(id).orElse(null);
        if (found != null) {
            found.setName(fabric.getName());
            found.setThickness(fabric.getThickness());
            RollerFabric updated = rollerFabricRepo.save(found);
            return Response.builder()
                    .data(mapToJson(updated))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Fabric not found"))
                    .status("error")
                    .build();
        }

    }

    /**
     * This method deletes a roller fabric from the database.
     *
     * @param fabricName The name of the fabric to be deleted
     * @return Response object with the deleted RollerFabric
     */

    public Response deleteRollerFabric(String fabricName) {
        RollerFabric found = rollerFabricRepo.findByName(fabricName).orElse(null);
        if (found != null) {
            rollerFabricRepo.delete(found);
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Fabric not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method calculates the weight of the fabric in kg
     * Not to be used in Controllers
     * @param width The width of the fabric
     * @param drop The drop of the fabric
     * @return The weight of the fabric in kg
     */
    public Measurement getWeightKg(RollerFabric fabric, Measurement width, Measurement drop){
        Measurement result = Measurement.builder().value(-1).build();
        Measurement currWeight = measurementConverter.convert(fabric.getWeight(), "kg/m2");
        width = measurementConverter.convert(width, "m");
        drop = measurementConverter.convert(drop, "m");

        if(currWeight.getValue() != -1 && width.getValue() != -1 && drop.getValue() != -1){
            result = Measurement.builder()
                    .value(currWeight.getValue() * width.getValue() * drop.getValue())
                    .unit("kg")
                    .build();
        }
        return result;
    }
}