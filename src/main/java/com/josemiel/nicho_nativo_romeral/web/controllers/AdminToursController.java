package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.app.services.TourService;
import com.josemiel.nicho_nativo_romeral.web.dto.request.*;
import com.josemiel.nicho_nativo_romeral.web.dto.response.TourPricingTierResponse;
import com.josemiel.nicho_nativo_romeral.web.dto.response.TourResponse;
import com.josemiel.nicho_nativo_romeral.web.dto.response.TourSessionResponse;
import com.josemiel.nicho_nativo_romeral.web.mappers.TourMapper;
import com.josemiel.nicho_nativo_romeral.web.mappers.TourPricingTierMapper;
import com.josemiel.nicho_nativo_romeral.web.mappers.TourSessionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/tours")
@RequiredArgsConstructor
public class AdminToursController {

    private final TourService tourService;
    private final TourMapper tourMapper;
    private final TourSessionMapper sessionMapper;
    private final TourPricingTierMapper tierMapper;

    // TOURS

    /**
     * Crea un nuevo tour.
     * 
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TourResponse> createTour(@Valid @RequestBody CreateTourRequest req) {
        var tour = tourService.createTour(req);
        return ResponseEntity.ok(tourMapper.toResponse(tour));
    }

    /**
     * Actualiza un tour existente.
     * 
     * @param tourId
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{tourId}")
    public ResponseEntity<TourResponse> updateTour(
            @PathVariable Integer tourId,
            @Valid @RequestBody UpdateTourRequest req) {
        var tour = tourService.updateTour(tourId, req);
        return ResponseEntity.ok(tourMapper.toResponse(tour));
    }

    /**
     * Elimina un tour por su ID.
     * 
     * @param tourId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{tourId}")
    public ResponseEntity<Void> deleteTour(@PathVariable Integer tourId) {
        tourService.deleteTour(tourId);
        return ResponseEntity.noContent().build();
    }

    // TOUR SESSIONS
    /**
     * Crea una nueva sesión para un tour específico.
     * 
     * @param tourId
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{tourId}/sessions")
    public ResponseEntity<TourSessionResponse> createSession(
            @PathVariable Integer tourId,
            @Valid @RequestBody CreateTourSessionRequest req) {
        var session = tourService.createSession(tourId, req);
        return ResponseEntity.ok(sessionMapper.toResponse(session));
    }

    /**
     * Actualiza una sesión existente para un tour específico.
     * 
     * @param sessionId
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<TourSessionResponse> updateSession(
            @PathVariable Integer sessionId,
            @Valid @RequestBody UpdateTourSessionRequest req) {
        var session = tourService.updateSession(sessionId, req);
        return ResponseEntity.ok(sessionMapper.toResponse(session));
    }

    /**
     * Elimina una sesión de tour por su ID.
     * 
     * @param sessionId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Integer sessionId) {
        tourService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    // TOUR PRICING TIERS

    /**
     * Crea un nuevo nivel de precios para un tour específico.
     * 
     * @param tourId
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{tourId}/pricing-tiers")
    public ResponseEntity<TourPricingTierResponse> createTier(
            @PathVariable Integer tourId,
            @Valid @RequestBody CreateTourPricingTierRequest req) {
        var tier = tourService.createPricingTier(tourId, req);
        return ResponseEntity.ok(tierMapper.toResponse(tier));
    }

    /**
     * Actualiza un nivel de precios existente.
     * 
     * @param tierId
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/pricing-tiers/{tierId}")
    public ResponseEntity<TourPricingTierResponse> updateTier(
            @PathVariable Integer tierId,
            @Valid @RequestBody UpdateTourPricingTierRequest req) {
        var tier = tourService.updatePricingTier(tierId, req);
        return ResponseEntity.ok(tierMapper.toResponse(tier));
    }

    /**
     * Elimina un nivel de precios por su ID.
     * 
     * @param tierId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/pricing-tiers/{tierId}")
    public ResponseEntity<Void> deleteTier(@PathVariable Integer tierId) {
        tourService.deletePricingTier(tierId);
        return ResponseEntity.noContent().build();
    }
}
