package com.vertilux.shadeCalculator.models.rollerShade;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.utils.MeasurementConverter;
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
@Table(name="roller_shade_fabric")
public class RollerFabric {
    @Transient
    private MeasurementConverter measurementConverter;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "thickness_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "thickness_unit"))
    })
    private Measurement thickness;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "weight_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "weight_unit"))
    })
    private Measurement weight;

    public Measurement getWeightKg(Measurement width, Measurement drop){
        Measurement result = Measurement.builder().value(-1).build();

        Measurement currWeight = measurementConverter.convert(weight, "kg/m");
        width = measurementConverter.convert(width, "m");
        drop = measurementConverter.convert(drop, "m");

        if(currWeight.getValue() != -1 && width.getValue() != -1 && drop.getValue() != -1){
            result = Measurement.builder()
                    .value(currWeight.getValue() * width.getValue() * drop.getValue())
                    .unit("kg")
                    .build();
        }
        return result;
    }

}
