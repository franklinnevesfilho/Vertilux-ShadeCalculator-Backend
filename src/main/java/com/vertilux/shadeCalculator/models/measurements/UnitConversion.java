package com.vertilux.shadeCalculator.models.measurements;

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
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private MeasurementUnit fromUnit;

    @ManyToOne
    private MeasurementUnit toUnit;

    private double conversionFactor;
}
