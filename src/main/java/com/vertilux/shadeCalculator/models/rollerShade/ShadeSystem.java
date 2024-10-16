package com.vertilux.shadeCalculator.models.rollerShade;


import com.vertilux.shadeCalculator.models.Measurement;
import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents a RollerShadeSystem
 * It is used to structure the data of a RollerShadeSystem
 * and gather all of its components.
 *
 * @version 1.0
 * @author Franklin Neves Filho
 */

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name="roller_systems")
public class ShadeSystem {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="name")
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "diameter_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "diameter_unit"))
    })
    private Measurement maxDiameter;
}
