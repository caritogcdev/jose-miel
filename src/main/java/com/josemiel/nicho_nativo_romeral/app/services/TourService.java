package com.josemiel.nicho_nativo_romeral.app.services;

import com.josemiel.nicho_nativo_romeral.domain.entities.Tour;
import com.josemiel.nicho_nativo_romeral.domain.entities.TourPricingTier;
import com.josemiel.nicho_nativo_romeral.domain.entities.TourSession;
import com.josemiel.nicho_nativo_romeral.domain.enums.TourSessionStatus;
import com.josemiel.nicho_nativo_romeral.domain.repositories.tours.TourPricingTierRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.tours.TourRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.tours.TourSessionRepository;
import com.josemiel.nicho_nativo_romeral.web.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepo;
    private final TourSessionRepository sessionRepo;
    private final TourPricingTierRepository tierRepo;

    // Pública

    /**
     * Lista todos los tours disponibles.
     * 
     * @return
     */
    public List<Tour> listTours() {
        return tourRepo.findAll();
    }

    /**
     * Lista las sesiones de un tour en un rango de fechas.
     * 
     * @param tourId
     * @param from
     * @param to
     * @return
     */
    public List<TourSession> listSessions(Integer tourId, LocalDateTime from, LocalDateTime to) {
        LocalDateTime f = (from != null) ? from : LocalDateTime.now().minusYears(1);
        LocalDateTime t = (to != null) ? to : LocalDateTime.now().plusYears(1);
        return sessionRepo.findByTourIdAndStartAtBetween(tourId, f, t);
    }

    // Parte de la administración (ADMIN)
    // TOURS

    /**
     * Crea un nuevo tour.
     * 
     * @param req
     * @return
     */
    @Transactional
    public Tour createTour(CreateTourRequest req) {
        Tour tour = Tour.builder()
                .name(req.name())
                .description(req.description())
                .journeyDuration(req.journeyDuration())
                .usersPerTour(req.usersPerTour())
                .active(req.active() == null ? true : req.active())
                .build();
        return tourRepo.save(tour);
    }

    /**
     * Actualiza un tour existente.
     * 
     * @param id
     * @param req
     * @return
     */
    @Transactional
    public Tour updateTour(Integer id, UpdateTourRequest req) {
        Tour tour = tourRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tour no encontrado"));
        tour.setName(req.name());
        tour.setDescription(req.description());
        tour.setJourneyDuration(req.journeyDuration());
        tour.setUsersPerTour(req.usersPerTour());
        tour.setActive(req.active());
        return tourRepo.save(tour);
    }

    /**
     * Elimina un tour.
     * 
     * @param id
     */
    @Transactional
    public void deleteTour(Integer id) {
        // opcional: validar que no tenga sesiones futuras, etc.
        if (!tourRepo.existsById(id)) {
            throw new IllegalArgumentException("Tour no encontrado");
        }
        tourRepo.deleteById(id);
    }

    // TOUR SESSIONS

    /**
     * Crea una nueva sesión para un tour.
     * 
     * @param tourId
     * @param req
     * @return
     */
    @Transactional
    public TourSession createSession(Integer tourId, CreateTourSessionRequest req) {
        Tour tour = tourRepo.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour no encontrado"));
        TourSession s = TourSession.builder()
                .tour(tour)
                .startAt(req.startAt())
                .endAt(req.endAt())
                .capacity(req.capacity())
                .status(TourSessionStatus.OPEN)
                .build();
        return sessionRepo.save(s);
    }

    /**
     * Actualiza una sesión existente.
     * 
     * @param sessionId
     * @param req
     * @return
     */
    @Transactional
    public TourSession updateSession(Integer sessionId, UpdateTourSessionRequest req) {
        TourSession s = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));
        s.setStartAt(req.startAt());
        s.setEndAt(req.endAt());
        s.setCapacity(req.capacity());
        s.setStatus(TourSessionStatus.valueOf(req.status().toUpperCase()));
        return sessionRepo.save(s);
    }

    /**
     * Elimina una sesión.
     * 
     * @param sessionId
     */
    @Transactional
    public void deleteSession(Integer sessionId) {
        if (!sessionRepo.existsById(sessionId)) {
            throw new IllegalArgumentException("Sesión no encontrada");
        }
        sessionRepo.deleteById(sessionId);
    }

    // TOUR PRICING TIERS

    /**
     * Crea una nueva tarifa para un tour.
     * 
     * @param tourId
     * @param req
     * @return
     */
    @Transactional
    public TourPricingTier createPricingTier(Integer tourId, CreateTourPricingTierRequest req) {
        Tour tour = tourRepo.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour no encontrado"));
        TourPricingTier tier = TourPricingTier.builder()
                .tour(tour)
                .minUser(req.minUser())
                .maxUser(req.maxUser())
                .pricePerPerson(req.pricePerPerson())
                .validFrom(req.validFrom())
                .validTo(req.validTo())
                .build();
        return tierRepo.save(tier);
    }

    /**
     * Actualiza una tarifa existente.
     * 
     * @param tierId
     * @param req
     * @return
     */
    @Transactional
    public TourPricingTier updatePricingTier(Integer tierId, UpdateTourPricingTierRequest req) {
        TourPricingTier tier = tierRepo.findById(tierId)
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada"));
        tier.setMinUser(req.minUser());
        tier.setMaxUser(req.maxUser());
        tier.setPricePerPerson(req.pricePerPerson());
        tier.setValidFrom(req.validFrom());
        tier.setValidTo(req.validTo());
        return tierRepo.save(tier);
    }

    /**
     * Elimina una tarifa.
     * 
     * @param tierId
     */
    @Transactional
    public void deletePricingTier(Integer tierId) {
        if (!tierRepo.existsById(tierId)) {
            throw new IllegalArgumentException("Tarifa no encontrada");
        }
        tierRepo.deleteById(tierId);
    }

}
