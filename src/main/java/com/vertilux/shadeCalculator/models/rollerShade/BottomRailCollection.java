package com.vertilux.shadeCalculator.models.rollerShade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertilux.shadeCalculator.models.Measurement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "bottom_rail_collections")
public class BottomRailCollection {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @ManyToOne
    private Measurement weight;

    @OneToMany(
            mappedBy = "bottomRailCollection",
            cascade = CascadeType.ALL
    )
    private List<BottomRail> bottomRails;
}
