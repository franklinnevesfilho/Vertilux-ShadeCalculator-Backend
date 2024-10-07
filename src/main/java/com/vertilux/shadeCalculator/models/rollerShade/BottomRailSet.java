package com.vertilux.shadeCalculator.models.rollerShade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name="bottom_rail_sets")
public class BottomRailSet {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "weight_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "weight_unit"))
    })
    private Measurement weight;
}
