package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.schemas.GetRollUp;
import com.vertilux.shadeCalculator.schemas.Schema;
import com.vertilux.shadeCalculator.services.CalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

/**
 * Calculator Controller
 * This class handles all the endpoints related to the calculator
 */
@AllArgsConstructor
@RestController
@RequestMapping("/calculator")
public class CalculatorController extends MainController{
    private CalculatorService calculatorService;

    private final Function<Schema, Response> getRollUp = (getRollUp) ->
            calculatorService.getRollUp((GetRollUp)getRollUp);

    @PostMapping("/getRollUp")
    public ResponseEntity<Response> getRollUp(GetRollUp object){
        return request(getRollUp, object);
    }
}
