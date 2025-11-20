package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.app.services.BookingService;
import com.josemiel.nicho_nativo_romeral.web.dto.response.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingsController {
    private final BookingService bookingService;

    /**
     * ADMIN: listar todas las reservas del sistema.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> listAll() {
        return ResponseEntity.ok(bookingService.listAllAdmin());
    }

    /**
     * ADMIN: ver una reserva por id.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getByIdAdmin(id));
    }
}
