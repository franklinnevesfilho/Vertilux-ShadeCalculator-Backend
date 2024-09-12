package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.schemas.GetRollUp;
import com.vertilux.shadeCalculator.schemas.Schema;
import com.vertilux.shadeCalculator.schemas.SystemLimitRequest;
import com.vertilux.shadeCalculator.services.CalculatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final Function<Schema, Response> getRollUp = (getRollUp) ->
            calculatorService.getRollUp((GetRollUp)getRollUp);
    private final Function<Schema, Response> getSystemLimit = (systemLimitRequest) ->
            calculatorService.getSystemLimit((SystemLimitRequest) systemLimitRequest);
//    private final Function<Schema, Response> getTubeDeflection = (shadeProposal) ->
//            calculatorService.getTubeDeflection((SystemLimitRequest) shadeProposal);

    @PostMapping("/getRollUp")
    public ResponseEntity<Response> getRollUp(@RequestBody GetRollUp rollUp){
        log.info("Received request to get roll up");
        return request(getRollUp, rollUp);
    }

    @PostMapping("/systemLimit")
    public ResponseEntity<Response> getSystemLimit(@RequestBody SystemLimitRequest systemLimitRequest){
        log.info("Received request to get system limit");
        return request(getSystemLimit, systemLimitRequest);
    }

//    @PostMapping("/tubeDeflection")
//    public ResponseEntity<Response> getTubeDeflection(@RequestBody SystemLimitRequest shadeProposal){
//        log.info("Received request to get tube deflection");
//        return request(getTubeDeflection, shadeProposal);
//    }

}
