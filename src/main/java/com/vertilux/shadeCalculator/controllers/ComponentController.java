package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.schemas.RollerFabricCreation;
import com.vertilux.shadeCalculator.schemas.RollerTubeCreation;
import com.vertilux.shadeCalculator.schemas.Schema;
import com.vertilux.shadeCalculator.services.RollerFabricService;
import com.vertilux.shadeCalculator.services.RollerTubeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <h1>ItemController</h1>
 *
 * This class handles all the crud operations for the items in the system.
 * including:
 * - RollerTubes
 * - Fabrics
 * - Components
 */

@AllArgsConstructor
@RestController
@RequestMapping("/components")
public class ComponentController extends MainController {
    private RollerTubeService rollerTubeService;
    private RollerFabricService fabricService;

    private final Supplier<Response> getAllTubes = () -> rollerTubeService.getAllRollerTubes();
    private final Function<String, Response> getTubeByName = (name) -> rollerTubeService.getRollerTubeByName(name);
    private final Function<String, Response> getTubeById = (id) -> rollerTubeService.getRollerTubeById(id);
    private final Function<Schema, Response> saveTube = (tube) -> rollerTubeService.createRollerTube((RollerTubeCreation) tube);
    private final Function<String, Response> deleteTube = (id) -> rollerTubeService.deleteRollerTube(id);
    private final BiFunction<String, Schema, Response> updateTube = (id, tube) -> rollerTubeService.updateRollerTube(id, (RollerTubeCreation) tube);

    private final Supplier<Response> getAllFabrics = () -> fabricService.getAllRollerFabrics();
    private final Function<String, Response> getFabricByName = (name) -> fabricService.getRollerFabricByName(name);
    private final Function<String, Response> getFabricById = (id) -> fabricService.getRollerFabricById(id);
    private final Function<Schema, Response> saveFabric = (fabric) -> fabricService.createRollerFabric((RollerFabricCreation) fabric);
    private final Function<String, Response> deleteFabric = (id) -> fabricService.deleteRollerFabric(id);
    private final BiFunction<String, Schema, Response> updateFabric = (id, fabric) -> fabricService.updateRollerFabric(id, (RollerFabricCreation) fabric);


    @PostMapping("/{serviceName}/save")
    public ResponseEntity<Response> save(@PathVariable("serviceName") String serviceName, @RequestBody Schema object){
        return switch (serviceName) {
            case "tube" -> request(saveTube, object);
            case "fabric" -> request(saveFabric, object);
            default -> factory.createBadRequestResponse();
        };
    }

    @GetMapping("/{serviceName}/get-all")
    public ResponseEntity<Response> getAll(@PathVariable("serviceName") String serviceName){
        return switch (serviceName) {
            case "tube" -> getAll(getAllTubes);
            case "fabric" -> getAll(getAllFabrics);
            default -> factory.createBadRequestResponse();
        };
    }

    @GetMapping("/{serviceName}/get-by-name")
    public ResponseEntity<Response> getByName(@PathVariable("serviceName") String serviceName, @RequestParam String name){
        return switch (serviceName) {
            case "tube" -> getByParam(getTubeByName, name);
            case "fabric" -> getByParam(getFabricByName, name);
            default -> factory.createBadRequestResponse();
        };
    }

    @GetMapping("/{serviceName}/get-by-id")
    public ResponseEntity<Response> getById(@PathVariable("serviceName") String serviceName, @RequestParam String id){
        return switch (serviceName) {
            case "tube" -> getByParam(getTubeById, id);
            case "fabric" -> getByParam(getFabricById, id);
            default -> factory.createBadRequestResponse();
        };
    }

    @DeleteMapping("/{serviceName}/delete")
    public ResponseEntity<Response> delete(@PathVariable("serviceName") String serviceName, @RequestParam String id){
        return switch (serviceName) {
            case "tube" -> getByParam(deleteTube, id);
            case "fabric" -> getByParam(deleteFabric, id);
            default -> factory.createBadRequestResponse();
        };
    }

    @PutMapping("/{serviceName}/update")
    public ResponseEntity<Response> update(@PathVariable("serviceName") String serviceName, @RequestParam String id, @RequestBody Schema object){
        return switch (serviceName) {
            case "tube" -> getByTwoParam(updateTube, id, object);
            case "fabric" -> getByTwoParam(updateFabric, id, object);
            default -> factory.createBadRequestResponse();
        };
    }

}
