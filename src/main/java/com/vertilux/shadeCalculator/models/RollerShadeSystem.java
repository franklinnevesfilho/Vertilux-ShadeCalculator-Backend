package com.vertilux.shadeCalculator.models;


import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents a RollerShadeSystem
 * It is used to structure the data of a RollerShadeSystem
 * and gather all of its components.
 *
 * @version 1.0
 * @author Franklin Neves Filho
 */

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name="roller_systems")
public class RollerShadeSystem {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="name")
    private String name;
}
