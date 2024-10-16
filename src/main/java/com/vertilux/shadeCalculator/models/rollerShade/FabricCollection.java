package com.vertilux.shadeCalculator.models.rollerShade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertilux.shadeCalculator.models.Measurement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "fabric_collections")
public class FabricCollection {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @ManyToOne
    private Measurement thickness;
    @ManyToOne
    private Measurement weight;

    @OneToMany(
            mappedBy = "fabricCollection",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Fabric> fabrics;

}
