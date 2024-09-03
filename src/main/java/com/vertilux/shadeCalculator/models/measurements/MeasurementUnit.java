package com.vertilux.shadeCalculator.models.measurements;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="measurement_units")
public class MeasurementUnit {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="unit")
    private String unit;

    @OneToMany(
            mappedBy = "fromUnit",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UnitConversion> fromConversions;

    @OneToMany(
            mappedBy = "toUnit",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UnitConversion> toConversions;
}
