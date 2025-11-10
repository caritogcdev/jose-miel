package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class Tour extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "journey_duration", nullable = false)
    private Integer journeyDuration; // minutos

    @Column(name = "users_per_tour", nullable = false)
    private Integer usersPerTour; // cupo por defecto

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean active = true;

}
