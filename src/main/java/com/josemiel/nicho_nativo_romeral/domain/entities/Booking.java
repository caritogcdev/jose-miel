package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;
import com.josemiel.nicho_nativo_romeral.domain.enums.BookingStatus;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings") // Nombre de la tabla en la DB
// @Data Esta anotación trae: @Getter + @Setter + @ToString + @EqualsAndHashCode
// + @RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true) // Patrón de diseño para crear clases
/**
 * Sin el patrón de diseño builder, las clases se crean de la
 * siguiente manera:
 * Clase clase = new Clase
 * Clase.setName("dsdsfd")
 * clase.setAge(23);
 *
 * Con el patrón de diseño builder la creamos de la siguiente manera
 * por ejemplo::
 * Clase.builder()
 * .name("ddsfdf")
 * .age(12)
 * .build();
 */
@ToString(exclude = {"user","tourSession"}) // evitar logs que no son necesarios acá
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class Booking extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "quantity_visitors", nullable = false)
    private Integer quantityVisitors;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = BookingStatus.DRAFT;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil; // bloqueo de cupos mientras el pago está pendiente

    // Relaciones con otras entidades
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_session_id", referencedColumnName = "id", nullable = false)
    private TourSession tourSession;

}
