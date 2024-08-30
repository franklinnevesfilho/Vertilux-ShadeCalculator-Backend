package com.vertilux.shadeCalculator.utils;

import com.vertilux.shadeCalculator.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ResponseFactory
 * <p>
 * This class is responsible for generating the responses sent to the server.
 * It handles the creation of the responses and the status codes.
 * </p>
 *
 * @author Franklin Neves Filho
 */

@Service
public class ResponseFactory {

    /**
     * @param response the response object to be sent to the server
     * @return An instance of ResponseEntity<Response> with a default status code of 200
     */
    public ResponseEntity<Response> createOkResponse(Response response) {
        return generateResponse(response, HttpStatus.OK);
    }

    /**
     * @return An instance of ResponseEntity<Response> with a default status code of 400
     */
    public ResponseEntity<Response> createBadRequestResponse() {
        return generateResponse(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param errors a list of strings representing errors that occurred
     * @return An instance of ResponseEntity<Response> with a default status code of 400
     */
    public ResponseEntity<Response> createBadRequestResponse(List<String> errors) {
        Response response = Response.builder().errors(errors).build();
        return generateResponse(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return An instance of ResponseEntity<Response> with a default status code of 204
     */
    public ResponseEntity<Response> createNoContentResponse() {
        return generateResponse(null, HttpStatus.NO_CONTENT);
    }

    /**
     * @param response a response object intended to be sent to the server
     * @param status the http status of the response
     * @return An instance of ResponseEntity<Response> with a default status code of 404
     */
    public ResponseEntity<Response> createResponse(Response response, HttpStatus status) {
        return generateResponse(null, HttpStatus.NOT_FOUND);
    }

    /**
     * @param payload a response object intended to be sent to the server
     * @param status  the http status of the response
     * @return An instance of ResponseEntity<Response> with a default status code of 200
     */

    private ResponseEntity<Response> generateResponse(Response payload, HttpStatus status) {
        Response response = Response.builder().build();
        if (payload != null) {
            response = payload;
        }
        return new ResponseEntity<>(response, status);
    }
}
