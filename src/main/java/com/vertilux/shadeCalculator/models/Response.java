package com.vertilux.shadeCalculator.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to represent a response from the server
 *
 * @version 1.0
 * @author Franklin Neves Filho
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
    private JsonNode data;

    @Builder.Default
    private List<String> errors = new LinkedList<>();
}
