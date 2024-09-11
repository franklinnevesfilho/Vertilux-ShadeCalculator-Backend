package com.vertilux.shadeCalculator.models.measurements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name="measurement_units")
public class MeasurementUnit {
    @Id
    @JsonIgnore
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="unit")
    private String unit;
}
