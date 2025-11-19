package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.app.services.BookingService;
import com.josemiel.nicho_nativo_romeral.web.dto.request.CreateBookingRequest;
import com.josemiel.nicho_nativo_romeral.web.dto.response.BookingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingsController {
    private final BookingService service;

    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest req) {
        return ResponseEntity.ok(service.create(req));
    }
}
