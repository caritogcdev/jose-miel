package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tour_pricing_tiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "tour")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class TourPricingTier extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "min_user", nullable = false)
    private Integer minUser;

    @Column(name = "max_user")
    private Integer maxUser; // NULL = en adelante

    @Column(name = "price_per_person", precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerPerson;

    // @Builder.Default
    // @Column(name = "currency", length = 3, nullable = false)
    // private String currency = "COP";

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id")
    private Tour tour;

}
