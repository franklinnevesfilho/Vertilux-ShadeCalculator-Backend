package com.vertilux.shadeCalculator.services;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.BottomRailSet;
import com.vertilux.shadeCalculator.repositories.BottomRailRepo;
import com.vertilux.shadeCalculator.schemas.BottomRailCreation;
import com.vertilux.shadeCalculator.utils.MeasurementConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * BottomRailService
 * This class is responsible for handling the business logic for the BottomRail entity.
 * @see BottomRailSet
 * @author Franklin Neves Filho
 */

@Slf4j
@AllArgsConstructor
@Service
public class BottomRailService extends MainService {
    private final BottomRailRepo bottomRailRepo;
    private final MeasurementConverter measurementConverter;

    /**
     * This method returns all the bottom rails in the database.
     *
     * @return Response object with all the bottom rails
     */
    public Response getAllBottomRails() {
        List<BottomRailSet> bottomRailSets = bottomRailRepo.findAll();

        return Response.builder()
                .data(mapToJson(bottomRailSets))
                .build();
    }

    /**
     * This method returns a bottom rail by its name.
     *
     * @param bottomRailName The name of the bottom rail to be found
     * @return Response object with the found BottomRail
     */
    public Response getBottomRailByName(String bottomRailName) {
        BottomRailSet found = bottomRailRepo.findByName(bottomRailName).orElse(null);
        if (found != null) {
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Bottom rail not found"))
                    .status("error")
                    .build();
        }

    }

    /**
     * This method returns a bottom rail by its id.
     *
     * @param bottomRailId The id of the bottom rail to be found
     * @return Response object with the found BottomRail
     */
    public Response getBottomRailById(String bottomRailId) {
        BottomRailSet found = bottomRailRepo.findById(bottomRailId).orElse(null);
        if (found != null) {
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Bottom rail not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method saves a bottom rail in the database.
     *
     * @param bottomRail The BottomRail object to be saved
     * @return Response object with the saved BottomRail
     */
    public Response saveBottomRail(BottomRailCreation bottomRail) {
        Optional<BottomRailSet> found = bottomRailRepo.findByName(bottomRail.getName());

        if (found.isPresent()){
            return Response.builder()
                    .errors(List.of("Bottom rail already exists"))
                    .status("error")
                    .build();
        } else {
            BottomRailSet newBottomRailSet = BottomRailSet.builder()
                    .name(bottomRail.getName())
                    .weight(bottomRail.getWeight())
                    .build();
            BottomRailSet savedBottomRailSet = bottomRailRepo.save(newBottomRailSet);
            return Response.builder()
                    .data(mapToJson(savedBottomRailSet))
                    .build();
        }
    }

    /**
     * This method deletes a bottom rail from the database.
     *
     * @param bottomRailId The id of the bottom rail to be deleted
     * @return Response object with status code 204
     */
    public Response deleteBottomRail(String bottomRailId) {
        BottomRailSet found = bottomRailRepo.findById(bottomRailId).orElse(null);
        if (found != null) {
            bottomRailRepo.deleteById(bottomRailId);
            return Response.builder()
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Bottom rail not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method updates a bottom rail in the database.
     *
     * @param bottomRailId The id of the bottom rail to be updated
     * @param bottomRail The BottomRail object to be updated
     * @return Response object with the updated BottomRail
     */
    public Response updateBottomRail(String bottomRailId, BottomRailCreation bottomRail) {
        Optional<BottomRailSet> found = bottomRailRepo.findById(bottomRailId);
        if (found.isPresent()) {
            BottomRailSet updatedBottomRailSet = found.get();
            updatedBottomRailSet.setName(bottomRail.getName());
            updatedBottomRailSet.setWeight(bottomRail.getWeight());
            BottomRailSet savedBottomRailSet = bottomRailRepo.save(updatedBottomRailSet);
            return Response.builder()
                    .data(mapToJson(savedBottomRailSet))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Bottom rail not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method calculates the weight of a bottom rail in kg.
     * Not to be used in Controllers
     * @param width The width of the bottom rail
     * @return The weight of the bottom rail in kg
     */
    public Measurement getWeightKg(BottomRailSet bottomRailSet, Measurement width) {
        Measurement result = Measurement.builder().value(-1).build();

        Measurement currWeight = measurementConverter.convert(bottomRailSet.getWeight(), "kg/m");
        width = measurementConverter.convert(width, "m");

        if (currWeight.getValue() != -1 && width.getValue() != -1) {
            result = Measurement.builder()
                    .value(currWeight.getValue() * width.getValue())
                    .unit("kg")
                    .build();
        }
        return result;
    }
}
