package com.josemiel.nicho_nativo_romeral.app.services;

import com.josemiel.nicho_nativo_romeral.domain.entities.Invoice;
import com.josemiel.nicho_nativo_romeral.domain.repositories.invoices.InvoiceRepository;
import com.josemiel.nicho_nativo_romeral.web.dto.response.InvoiceResponse;
import com.josemiel.nicho_nativo_romeral.web.mappers.InvoiceMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepo;
    private final InvoiceMapper invoiceMapper;

    // ADMIN

    /**
     * Obtener una factura por ID sin restricción de dueño (solo ADMIN).
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public InvoiceResponse getByIdAdmin(Integer id) {
        Invoice inv = invoiceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));
        return invoiceMapper.toResponse(inv);
    }

    /**
     * Lista todas las facturas (solo ADMIN).
     * @return
     */
    @Transactional(readOnly = true)
    public List<InvoiceResponse> listAllAdmin() {
        return invoiceRepo.findAll()
                .stream()
                .map(invoiceMapper::toResponse)
                .toList();
    }

    // USER (dueño)

    /**
     * Obtener una factura por ID, verificando que sea del usuario (por email).
     * @param id
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public InvoiceResponse getMyInvoiceById(Integer id, String userEmail) {
        Invoice inv = invoiceRepo.findByIdAndBooking_User_Email(id, userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada o no te pertenece"));
        return invoiceMapper.toResponse(inv);
    }

    /**
     * Obtener la factura asociada a una reserva concreta, verificando que sea del usuario (por email).
     * @param bookingId
     * @param userEmail
     * @return
     */

    @Transactional(readOnly = true)
    public InvoiceResponse getMyInvoiceByBookingId(Integer bookingId, String userEmail) {
        Invoice inv = invoiceRepo.findByBookingIdAndBooking_User_Email(bookingId, userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada para esa reserva o no te pertenece"));
        return invoiceMapper.toResponse(inv);
    }

    /**
     * Lista todas las facturas asociadas al email del usuario.
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public List<InvoiceResponse> listMyInvoices(String userEmail) {
        return invoiceRepo.findByBooking_User_Email(userEmail)
                .stream()
                .map(invoiceMapper::toResponse)
                .toList();
    }
}
