package com.vertilux.shadeCalculator.models.rollerShade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.utils.MeasurementConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name="bottom_rails")
public class BottomRail {
    @Transient
    private MeasurementConverter measurementConverter;

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

    public Measurement getWeightKg(Measurement width) {
        Measurement result = Measurement.builder().value(-1).build();

        Measurement currWeight = measurementConverter.convert(this.weight, "kg/m");
        width = measurementConverter.convert(width, "m");

        if (currWeight.getValue() != -1 && width.getValue() != -1) {
            result = Measurement.builder()
                    .value(currWeight.getValue() * width.getValue())
                    .unit("kg")
                    .build();
        }
        return result;
    }
}
