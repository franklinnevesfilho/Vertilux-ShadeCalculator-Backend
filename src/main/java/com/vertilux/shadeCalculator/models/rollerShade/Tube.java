package com.vertilux.shadeCalculator.models.rollerShade;

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
public class Tube {
    @Id
    private String id;
    private String description;

    @ManyToOne
    private TubeCollection tubeCollection;

}
