package com.vertilux.shadeCalculator.models.rollerShade;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.utils.MeasurementConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="roller_shade_fabric")
public class RollerFabric {
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
}
