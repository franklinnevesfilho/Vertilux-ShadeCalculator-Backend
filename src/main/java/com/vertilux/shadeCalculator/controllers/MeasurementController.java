package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.services.MeasurementService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * MeasurementController
 * This class is the controller for the Measurements the application is able to convert.
 * Ideally these endpoints will only be accessible by Admins
 *
 * @see MeasurementService for more info
 * @author Franklin Neves Filho
 */

@AllArgsConstructor
@RestController("/measurement")
public class MeasurementController extends MainController{
    private MeasurementService measurementService;

    private final Supplier<Response> getAllUnits = () -> measurementService.getAllUnits();
    private final Supplier<Response> getAllConversions = () -> measurementService.getAllConversions();
    private final Function<String, Response> getUnitById = (id) -> measurementService.getUnitById(id);
    private final Function<String, Response> getUnitByName = (name) -> measurementService.getUnitByName(name);
    private final Function<String, Response> deleteUnitById = (id) -> measurementService.deleteUnitById(id);
    private final Function<String, Response> deleteUnitByName = (name) -> measurementService.deleteUnitByName(name);

    @GetMapping("/all-units")
    public ResponseEntity<Response> getAllUnits() {
        return getAll(getAllUnits);
    }

    @GetMapping("/all-conversions")
    public ResponseEntity<Response> getAllConversions(){
        return getAll(getAllConversions);
    }

    @GetMapping("/unit")
    public ResponseEntity<Response> getMeasurement(@PathParam("unitId") String unitId, @PathParam("unitName") String unitName){
       if(unitId != null){
           return getByParam(getUnitById, unitId);
       } else if (unitName != null){
           return getByParam(getUnitByName, unitName);
       } else{
           return factory.createBadRequestResponse();
       }
    }

    @DeleteMapping("/unit")
    public ResponseEntity<Response> deleteMeasurement(@PathParam("unitId") String unitId, @PathParam("unitName") String unitName){
        if(unitId != null){
            return getByParam(deleteUnitById, unitId);
        } else if (unitName != null){
            return getByParam(deleteUnitByName, unitName);
        } else{
            return factory.createBadRequestResponse();
        }
    }

}
