package com.vertilux.shadeCalculator.services;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.BottomRail;
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
 * @see BottomRail
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
        List<BottomRail> bottomRails = bottomRailRepo.findAll();

        return Response.builder()
                .data(mapToJson(bottomRails))
                .build();
    }

    /**
     * This method returns a bottom rail by its name.
     *
     * @param bottomRailName The name of the bottom rail to be found
     * @return Response object with the found BottomRail
     */
    public Response getBottomRailByName(String bottomRailName) {
        BottomRail found = bottomRailRepo.findByName(bottomRailName).orElse(null);
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
        BottomRail found = bottomRailRepo.findById(bottomRailId).orElse(null);
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
        Optional<BottomRail> found = bottomRailRepo.findByName(bottomRail.getName());

        if (found.isPresent()){
            return Response.builder()
                    .errors(List.of("Bottom rail already exists"))
                    .status("error")
                    .build();
        } else {
            BottomRail newBottomRail = BottomRail.builder()
                    .name(bottomRail.getName())
                    .weight(bottomRail.getWeight())
                    .build();
            BottomRail savedBottomRail = bottomRailRepo.save(newBottomRail);
            return Response.builder()
                    .data(mapToJson(savedBottomRail))
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
        BottomRail found = bottomRailRepo.findById(bottomRailId).orElse(null);
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
        Optional<BottomRail> found = bottomRailRepo.findById(bottomRailId);
        if (found.isPresent()) {
            BottomRail updatedBottomRail = found.get();
            updatedBottomRail.setName(bottomRail.getName());
            updatedBottomRail.setWeight(bottomRail.getWeight());
            BottomRail savedBottomRail = bottomRailRepo.save(updatedBottomRail);
            return Response.builder()
                    .data(mapToJson(savedBottomRail))
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
    public Measurement getWeightKg(BottomRail bottomRail, Measurement width) {
        Measurement result = Measurement.builder().value(-1).build();

        Measurement currWeight = measurementConverter.convert(bottomRail.getWeight(), "kg/m");
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
