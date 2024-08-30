package com.vertilux.shadeCalculator.models;


import jakarta.persistence.*;
import lombok.*;

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
