package com.vertilux.shadeCalculator.models.rollerShade;

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
    @Id
    private String id;
    private String description;

    @ManyToOne
    private BottomRailCollection bottomRailCollection;
}
