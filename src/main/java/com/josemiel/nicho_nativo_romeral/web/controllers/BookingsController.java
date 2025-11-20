package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.app.services.BookingService;
import com.josemiel.nicho_nativo_romeral.web.dto.request.CreateBookingRequest;
import com.josemiel.nicho_nativo_romeral.web.dto.response.BookingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingsController {
    private final BookingService service;

    /**
     * Crear una reserva (usuario autenticado).
     * 
     * @param req
     * @param auth
     * @return
     */
    @PostMapping
    public ResponseEntity<BookingResponse> create(
            @Valid @RequestBody CreateBookingRequest req,
            Authentication auth) {
        String email = auth.getName(); // viene del JWT
        return ResponseEntity.ok(service.create(req, email));
    }

     /**
     * Listar TODAS las reservas del usuario autenticado.
     */
    @GetMapping("/me")
    public ResponseEntity<List<BookingResponse>> myBookings(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(service.listMyBookings(email));
    }

    /**
     * Ver una reserva concreta que pertenezca al usuario autenticado.
     */
    @GetMapping("/me/{id}")
    public ResponseEntity<BookingResponse> myBooking(
            @PathVariable Integer id,
            Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(service.getMyBooking(id, email));
    }
}
