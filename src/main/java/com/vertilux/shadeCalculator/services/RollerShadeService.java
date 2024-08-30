package com.vertilux.shadeCalculator.services;


import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.RollerShadeSystem;
import com.vertilux.shadeCalculator.repositories.RollerShadeRepo;
import com.vertilux.shadeCalculator.schemas.RollerShadeSystemCreation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * RollerShadeService
 * <p>
 *     This service class handles the basic crud operations for the RollerShadeSystem entity.
 * </p>
 *
 * @author Franklin Neves Filho
 */

@Slf4j
@AllArgsConstructor
@Service
public class RollerShadeService extends MainService {
    private final RollerShadeRepo rollerShadeRepo;


    /**
     * @return A Response object with all the RollerShadeSystems
     */
    public Response getAll() {
        List<RollerShadeSystem> systems = rollerShadeRepo.findAll();
        return Response.builder().data(mapToJson(systems)).build();
    }

    /**
     * @param system The RollerShadeSystemCreation object to be saved
     * @return A Response object with the saved RollerShadeSystem
     */
    public Response save(RollerShadeSystemCreation system) {
        Response response;

        Optional<RollerShadeSystem> found = rollerShadeRepo.findByName(system.getName());
        if (found.isPresent()) {
            response = Response.builder().errors(List.of("System already exists")).build();
            return response;
        } else {
            RollerShadeSystem newSystem = RollerShadeSystem.builder()
                    .name(system.getName())
                    .build();
            RollerShadeSystem savedSystem = rollerShadeRepo.save(newSystem);
            return Response.builder().data(mapToJson(savedSystem)).build();
        }

    }

    /**
     * @param id The id of the RollerShadeSystem to be deleted
     * @return A Response object with status code 204
     */
    public Response delete(String id) {
        rollerShadeRepo.deleteById(id);
        return Response.builder().build();
    }

    /**
     * @param id The id of the RollerShadeSystem to be updated
     * @param system The RollerShadeSystemCreation object to be updated
     * @return A Response object with the updated RollerShadeSystem
     */
    public Response update(String id, RollerShadeSystemCreation system) {
        Optional<RollerShadeSystem> found = rollerShadeRepo.findById(id);
        if (found.isPresent()) {
            RollerShadeSystem updatedSystem = found.get();
            updatedSystem.setName(system.getName());
            RollerShadeSystem savedSystem = rollerShadeRepo.save(updatedSystem);
            return Response.builder().data(mapToJson(savedSystem)).build();
        } else {
            return Response.builder().errors(List.of("System not found")).build();
        }
    }

    /**
     * @param id The id of the RollerShadeSystem to be retrieved
     * @return A Response object with the RollerShadeSystem
     */
    public Response getById(String id) {
        Optional<RollerShadeSystem> found = rollerShadeRepo.findById(id);
        if (found.isPresent()) {
            return Response.builder().data(mapToJson(found.get())).build();
        } else {
            return Response.builder().errors(List.of("System not found")).build();
        }
    }

    /**
     * @param name The name of the RollerShadeSystem to be retrieved
     * @return A Response object with the RollerShadeSystem
     */
    public Response getByName(String name) {
        Optional<RollerShadeSystem> found = rollerShadeRepo.findByName(name);
        if (found.isPresent()) {
            return Response.builder().data(mapToJson(found.get())).build();
        } else {
            return Response.builder().errors(List.of("System not found")).build();
        }
    }
}
