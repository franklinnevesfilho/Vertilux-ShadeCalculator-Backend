package com.vertilux.shadeCalculator.controllers;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.schemas.Schema;
import com.vertilux.shadeCalculator.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * MainController
 * This class is the main controller for the application.
 * All Controllers should extend this class, which is responsible for generating the responses sent to the server.
 *
 * @version 1.0
 * @author Franklin Neves Filho
 */
public class MainController {
    @Autowired
    protected ResponseFactory factory;


    /**
     * @param supply the Supplier method to be used
     * @return An instance of ResponseEntity<Response>
     */
    protected ResponseEntity<Response> getAll(Supplier<Response> supply){
        ResponseEntity<Response> responseEntity;
        Response result = supply.get();
        if(result == null){
            responseEntity = factory.createNoContentResponse();
        }else{
            responseEntity = factory.createOkResponse(supply.get());
        }
        return responseEntity;
    }

    /**
     * @param supply the Function method to be used
     * @param param the parameter to be used
     * @return An instance of ResponseEntity<Response> with the resulting status
     */
    protected ResponseEntity<Response> getByParam(Function<String,Response> supply, String param){
        ResponseEntity<Response> responseEntity;
        if(param != null && !param.isEmpty()){
            Response response = supply.apply(param);
            if(response == null){
                responseEntity = factory.createNoContentResponse();
            }else{
                responseEntity = factory.createOkResponse(response);
            }
        }else{
            responseEntity = factory.createBadRequestResponse();
        }
        return responseEntity;
    }

    /**
     * @param function the BiFunction method to be used
     * @param param the first parameter to be used (String)
     * @param object the second parameter to be used (Object)
     * @return An instance of ResponseEntity<Response> with the resulting status
     */
    protected ResponseEntity<Response> getByTwoParam(BiFunction<String, Schema, Response> function, String param, Schema object){
        ResponseEntity<Response> responseEntity;
        if(param != null && !param.isEmpty() && object != null){
            Response response = function.apply(param,object);
            if(response == null){
                responseEntity = factory.createNoContentResponse();
            }else{
                responseEntity = factory.createOkResponse(response);
            }
        }else{
            responseEntity = factory.createBadRequestResponse();
        }
        return responseEntity;
    }


    /**
     * @param function the Function method to be used
     * @param object the object to be used
     * @return An instance of ResponseEntity<Response> with the resulting status
     */
    protected ResponseEntity<Response> request(Function<Schema, Response> function, Schema object){
        ResponseEntity<Response> responseEntity;
        if(object != null){
            Response response = function.apply(object);
            if(response == null){
                responseEntity = factory.createNoContentResponse();
            }else{
                responseEntity = factory.createOkResponse(response);
            }
        }else{
            responseEntity = factory.createBadRequestResponse();
        }
        return responseEntity;
    }
}
