package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.schemas.GetRollUp;
import com.vertilux.shadeCalculator.schemas.Schema;
import com.vertilux.shadeCalculator.schemas.ShadeTemplate;
import com.vertilux.shadeCalculator.schemas.SystemLimitRequest;
import com.vertilux.shadeCalculator.services.CalculatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Calculator Controller
 * This class handles all the endpoints related to the calculator
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/calculator")
public class CalculatorController extends MainController{
    private CalculatorService calculatorService;

    private final BiFunction<String, Schema, Response> getRollUp = (unit, getRollUp) ->
            calculatorService.getRollUp(unit, (GetRollUp)getRollUp);
    private final BiFunction<String, Schema, Response> getSystemLimit = (unit, systemLimitRequest) ->
            calculatorService.getSystemLimit(unit, (SystemLimitRequest) systemLimitRequest);

    private final Function<Schema, Response> getDeflection = (template) ->
            calculatorService.getTubeDeflection((ShadeTemplate) template);

    @PostMapping("/getRollUp/{unit}")
    public ResponseEntity<Response> getRollUp(@PathVariable String unit, @RequestBody GetRollUp rollUp){
        log.info("Received request to get roll up");
        return getByTwoParam(getRollUp, unit, rollUp);
    }

    @PostMapping("/systemLimit/{unit}")
    public ResponseEntity<Response> getSystemLimit(@PathVariable String unit, @RequestBody SystemLimitRequest systemLimitRequest){
        log.info("Received request to get system limit");
        return getByTwoParam(getSystemLimit, unit, systemLimitRequest);
    }

    @PostMapping("/getDeflection")
    public ResponseEntity<Response> getDeflection(@RequestBody ShadeTemplate template){
        log.info("Received request to get deflection");
        return request(getDeflection, template);
    }

}
