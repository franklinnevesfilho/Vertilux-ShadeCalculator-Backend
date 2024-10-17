package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.schemas.BottomRailCreation;
import com.vertilux.shadeCalculator.schemas.FabricCollectionCreation;
import com.vertilux.shadeCalculator.schemas.RollerTubeCreation;
import com.vertilux.shadeCalculator.schemas.Schema;
import com.vertilux.shadeCalculator.services.BottomRailService;
import com.vertilux.shadeCalculator.services.FabricService;
import com.vertilux.shadeCalculator.services.TubeService;
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
    private TubeService tubeService;
    private FabricService fabricService;
    private BottomRailService bottomRailService;

    //=================================
    // Tube functions
    //=================================
    private final Supplier<Response> getAllTubes =
            () -> tubeService.getAllRollerTubes();
    private final Function<String, Response> getTubeByName =
            (name) -> tubeService.getRollerTubeByName(name);
    private final Function<String, Response> getTubeById =
            (id) -> tubeService.getRollerTubeById(id);
    private final Function<Schema, Response> saveTube =
            (tube) -> tubeService.createRollerTube((RollerTubeCreation) tube);
    private final Function<String, Response> deleteTube =
            (id) -> tubeService.deleteRollerTube(id);
    private final BiFunction<String, Schema, Response> updateTube =
            (id, tube) -> tubeService.updateRollerTube(id, (RollerTubeCreation) tube);

    //=================================
    // Fabric functions
    //=================================
    private final Supplier<Response> getAllFabrics =
            () -> fabricService.getAllFabrics();
    private final Function<Schema, Response> saveCollection =
            (fabric) -> fabricService.createCollection((FabricCollectionCreation) fabric);

    //=================================
    // BottomRail functions
    //=================================
    private final Supplier<Response> getAllBottomRails =
            () -> bottomRailService.getAllBottomRails();
    private final Function<String, Response> getBottomRailByName =
            (name) -> bottomRailService.getBottomRailByName(name);
    private final Function<String, Response> getBottomRailById =
            (id) -> bottomRailService.getBottomRailById(id);
    private final Function<Schema, Response> saveBottomRail =
            (bottomRail) -> bottomRailService.saveBottomRail((BottomRailCreation) bottomRail);
    private final Function<String, Response> deleteBottomRail =
            (id) -> bottomRailService.deleteBottomRail(id);
    private final BiFunction<String, Schema, Response> updateBottomRail =
            (id, bottomRail) -> bottomRailService.updateBottomRail(id, (BottomRailCreation) bottomRail);



    @PostMapping("/{serviceName}/save")
    public ResponseEntity<Response> save(@PathVariable("serviceName") String serviceName, @RequestBody Schema object){
        return switch (serviceName) {
            case "tube" -> request(saveTube, object);
            case "fabric" -> request(saveCollection, object);
            case "bottomrail" -> request(saveBottomRail, object);
            default -> factory.createBadRequestResponse();
        };
    }

    @GetMapping("/{serviceName}/get-all")
    public ResponseEntity<Response> getAll(@PathVariable("serviceName") String serviceName){
        return switch (serviceName) {
            case "tube" -> getAll(getAllTubes);
            case "fabric" -> getAll(getAllFabrics);
            case "bottomrail" -> getAll(getAllBottomRails);
            default -> factory.createBadRequestResponse();
        };
    }

    @GetMapping("/{serviceName}/get-by-name")
    public ResponseEntity<Response> getByName(@PathVariable("serviceName") String serviceName, @RequestParam String name){
        return switch (serviceName) {
            case "tube" -> getByParam(getTubeByName, name);
            case "bottomrail" -> getByParam(getBottomRailByName, name);
            default -> factory.createBadRequestResponse();
        };
    }

    @GetMapping("/{serviceName}/get-by-id")
    public ResponseEntity<Response> getById(@PathVariable("serviceName") String serviceName, @RequestParam String id){
        return switch (serviceName) {
            case "tube" -> getByParam(getTubeById, id);
            case "bottomrail" -> getByParam(getBottomRailById, id);
            default -> factory.createBadRequestResponse();
        };
    }

    @DeleteMapping("/{serviceName}/delete")
    public ResponseEntity<Response> delete(@PathVariable("serviceName") String serviceName, @RequestParam String id){
        return switch (serviceName) {
            case "tube" -> getByParam(deleteTube, id);
            case "bottomrail" -> getByParam(deleteBottomRail, id);
            default -> factory.createBadRequestResponse();
        };
    }

    @PutMapping("/{serviceName}/update")
    public ResponseEntity<Response> update(@PathVariable("serviceName") String serviceName, @RequestParam String id, @RequestBody Schema object){
        return switch (serviceName) {
            case "tube" -> getByTwoParam(updateTube, id, object);
            case "bottomrail" -> getByTwoParam(updateBottomRail, id, object);
            default -> factory.createBadRequestResponse();
        };
    }

}
