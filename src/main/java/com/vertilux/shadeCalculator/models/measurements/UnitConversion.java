package com.vertilux.shadeCalculator.models.measurements;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="unit_conversions")
public class UnitConversion {
    @Id
    @JsonIgnore
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private MeasurementUnit from;

    @ManyToOne
    private MeasurementUnit to;

    private double factor;
}
