package com.josemiel.nicho_nativo_romeral.domain.repositories.invoices;

import com.josemiel.nicho_nativo_romeral.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    /**
     * Encuentra una factura por su ID de reserva.
     * @param bookingId
     * @return
     */
    Optional<Invoice> findByBookingId(Integer bookingId);
    /**
     * Encuentra una factura por su consecutivo.
     * @param consecutive
     * @return
     */
    Optional<Invoice> findByConsecutive(String consecutive);
}
