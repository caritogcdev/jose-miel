package com.josemiel.nicho_nativo_romeral.domain.repositories.payments;

import com.josemiel.nicho_nativo_romeral.domain.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    /**
     * Encuentra un pago por su ID de transacción del proveedor.
     * @param providerTxId
     * @return
     */
    Optional<Payment> findByProviderTxId(String providerTxId);
}
