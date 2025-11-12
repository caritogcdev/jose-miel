package com.josemiel.nicho_nativo_romeral.domain.repositories.payments;

import com.josemiel.nicho_nativo_romeral.domain.entities.Payment;
import com.josemiel.nicho_nativo_romeral.domain.enums.PaymentStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    /**
     * Encuentra un pago por su ID de transacción del proveedor.
     * 
     * @param providerTxId
     * @return
     */
    Optional<Payment> findByProviderTxId(String providerTxId);

    /**
     * Encuentra todos los pagos asociados a una factura.
     * @param invoiceId
     * @return
     */
    List<Payment> findByInvoiceId(Integer invoiceId);

    /**
     * Encuentra todos los pagos que han expirado.
     * @param status
     * @param time
     * @return
     */
    List<Payment> findByStatusAndExpiresAtBefore(PaymentStatus status, LocalDateTime time);
}
