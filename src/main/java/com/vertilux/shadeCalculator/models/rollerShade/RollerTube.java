package com.vertilux.shadeCalculator.models.rollerShade;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="roller_tubes")
public class RollerTube {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "outer_diameter_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "outer_diameter_unit"))
    })
    private Measurement outerDiameter;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "inner_diameter_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "inner_diameter_unit"))
    })
    private Measurement innerDiameter;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "modulus_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "modulus_unit"))
    })
    @Builder.Default
    private Measurement modulus = Measurement
            .builder()
            .value(70)
            .unit("GPa")
            .build();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "density_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "density_unit"))
    })
    @Builder.Default
    private Measurement density = Measurement
            .builder()
            .value(2.7)
            .unit("g/cm^3")
            .build();


    /**
     * This method calculates the deflection of the tube
     * based on the load applied to it.
     * @param load The entire load applied to the tube
     * @return the deflection of the tube
     */
    public Measurement getTubeDeflection(Measurement load) {
        return Measurement.builder().build();

    }
}
