package com.vertilux.shadeCalculator.models.rollerShade;

import com.vertilux.shadeCalculator.models.Measurement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="tube_collections")
public class TubeCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @ManyToOne
    private Measurement outerDiameter;
    @ManyToOne
    private Measurement innerDiameter;
    @ManyToOne
    private Measurement modulus;
    @ManyToOne
    private Measurement density;

    @OneToMany(
            mappedBy = "tubeCollection",
            cascade = CascadeType.ALL
    )
    private List<Tube> tubes;
}
