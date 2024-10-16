package com.vertilux.shadeCalculator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Measurement
 * containing a value and a unit.
 *
 * @author Franklin Neves Filho
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="measurements")
public class Measurement {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double value;
    private String unit;
}
