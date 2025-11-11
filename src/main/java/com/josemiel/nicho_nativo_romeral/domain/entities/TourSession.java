package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;
import com.josemiel.nicho_nativo_romeral.domain.enums.TourSessionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tour_sessions", uniqueConstraints = @UniqueConstraint(name = "uk_ts_tour_start", columnNames = {
        "tour_id", "start_at" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "tour")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class TourSession extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at") // nullable = false
    private LocalDateTime endAt;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TourSessionStatus status = TourSessionStatus.OPEN;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id")
    private Tour tour;

}
