package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.schemas.Dto;
import com.vertilux.shadeCalculator.schemas.RollerShadeSystemCreation;
import com.vertilux.shadeCalculator.services.RollerShadeService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@AllArgsConstructor
@RestController
@RequestMapping("/roller-shade")
public class RollerShadeController extends MainController{
    private RollerShadeService rollerShadeService;

    private final Supplier<Response> getAll = () -> rollerShadeService.getAll();
    private final Function<Dto, Response> save = (system) -> rollerShadeService.save((RollerShadeSystemCreation) system);
    private final Function<String, Response> getByName = (name) -> rollerShadeService.getByName(name);
    private final Function<String, Response> getById = (id) -> rollerShadeService.getById(id);
    private final Function<String, Response> delete = (id) -> rollerShadeService.delete(id);
    private final BiFunction<String, Dto, Response> update = (id, update) -> rollerShadeService.update(id, (RollerShadeSystemCreation) update);

    @PostMapping("/save")
    public ResponseEntity<Response> save(@RequestBody RollerShadeSystemCreation system){
        return request(save, system);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll(){
        return getAll(getAll);
    }

    @GetMapping("/")
    public ResponseEntity<Response> getByName(@PathParam("name") String name, @PathParam("id") String id){
        if(name != null){
            return getByParam(getByName, name);
        }else if(id != null){
            return getByParam(getById, id);
        }else{
            return factory.createBadRequestResponse();
        }
    }

}
