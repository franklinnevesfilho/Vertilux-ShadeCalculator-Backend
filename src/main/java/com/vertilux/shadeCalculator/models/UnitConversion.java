package com.vertilux.shadeCalculator.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="conversions")
public class UnitConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String from;
    private String to;
    private double factor;
}
