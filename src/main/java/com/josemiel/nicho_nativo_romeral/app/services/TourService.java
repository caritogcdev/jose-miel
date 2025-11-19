package com.josemiel.nicho_nativo_romeral.app.services;

import com.josemiel.nicho_nativo_romeral.domain.entities.Tour;
import com.josemiel.nicho_nativo_romeral.domain.entities.TourSession;
import com.josemiel.nicho_nativo_romeral.domain.repositories.tours.TourRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.tours.TourSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepo;
    private final TourSessionRepository sessionRepo;

    /**
     * Lista todos los tours disponibles.
     * @return
     */
    public List<Tour> listTours() {
        return tourRepo.findAll();
    }

    // public List<TourSession> listSessions(Integer tourId, LocalDateTime from, LocalDateTime to) {
    //     return sessionRepo.findByTour_IdAndStartAtBetween(tourId, from, to);
    // }

    /**
     * Lista las sesiones de un tour en un rango de fechas.
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
}
