package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoices", uniqueConstraints = {
        @UniqueConstraint(name = "uk_invoice_booking", columnNames = "bookings_id"),
        @UniqueConstraint(name = "uk_invoice_consecutive", columnNames = "consecutive")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "booking")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class Invoice extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "consecutive", length = 45, nullable = false)
    private String consecutive;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    // @Column(name = "tax", precision = 10, scale = 2, nullable = false)
    // private BigDecimal tax;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    // Relaciones con otras entidades
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

}
