package com.josemiel.nicho_nativo_romeral.domain.repositories.invoices;

import com.josemiel.nicho_nativo_romeral.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
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

    /**
     * Encuentra todas las facturas asociadas al email de un usuario.
     * @param email
     * @return
     */
    List<Invoice> findByBooking_User_Email(String email);

    /**
     * Encuentra una factura por su ID y el email del usuario asociado a la reserva.
     * @param id
     * @param email
     * @return
     */
    Optional<Invoice> findByIdAndBooking_User_Email(Integer id, String email);

    /**
     * Encuentra una factura por su ID de reserva y el email del usuario asociado a la reserva.
     * @param bookingId
     * @param email
     * @return
     */
    Optional<Invoice> findByBookingIdAndBooking_User_Email(Integer bookingId, String email);
}
