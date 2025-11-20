package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.app.services.InvoiceService;
import com.josemiel.nicho_nativo_romeral.web.dto.response.InvoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoicesController {

    private final InvoiceService invoiceService;

    // USER: solo sus facturas

    /**
     * USER: listar todas MIS facturas.
     */
    @GetMapping("/me")
    public ResponseEntity<List<InvoiceResponse>> myInvoices(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(invoiceService.listMyInvoices(email));
    }

    /**
     * USER: ver una factura mía por id.
     */
    @GetMapping("/me/{id}")
    public ResponseEntity<InvoiceResponse> myInvoiceById(
            @PathVariable Integer id,
            Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(invoiceService.getMyInvoiceById(id, email));
    }

    /**
     * USER: ver la factura de una reserva mía.
     */
    @GetMapping("/me/by-booking/{bookingId}")
    public ResponseEntity<InvoiceResponse> myInvoiceByBooking(
            @PathVariable Integer bookingId,
            Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(invoiceService.getMyInvoiceByBookingId(bookingId, email));
    }

    // ADMIN: acceso completo

    /**
     * ADMIN: listar todas las facturas del sistema.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> listAllAdmin() {
        return ResponseEntity.ok(invoiceService.listAllAdmin());
    }

    /**
     * ADMIN: ver una factura por id, sin importar el dueño.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getByIdAdmin(@PathVariable Integer id) {
        return ResponseEntity.ok(invoiceService.getByIdAdmin(id));
    }
}
