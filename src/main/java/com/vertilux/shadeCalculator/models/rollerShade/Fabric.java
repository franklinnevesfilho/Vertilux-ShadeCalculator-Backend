package com.vertilux.shadeCalculator.models.rollerShade;

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
@Table(name="fabrics")
public class Fabric {
    @Id
    private String id;
    private String description;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private FabricCollection fabricCollection;
}
