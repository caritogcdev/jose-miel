package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;
import com.josemiel.nicho_nativo_romeral.domain.enums.PaymentProvider;
import com.josemiel.nicho_nativo_romeral.domain.enums.PaymentStatus;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = @Index(name = "idx_pay_provider_tx", columnList = "provider_tx_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "invoice")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class Payment extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private PaymentProvider provider = PaymentProvider.PAYU;

    @Column(name = "provider_tx_id", length = 80, unique = true)
    private String providerTxId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    // @Builder.Default
    // @Column(name = "currency", length = 3, nullable = false)
    // private String currency = "COP";

    @Column(name = "authorization_code", length = 40)
    private String authorizationCode;

    @Column(name = "error_code", length = 40)
    private String errorCode;

    @Column(name = "error_message", length = 255)
    private String errorMessage;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

}
