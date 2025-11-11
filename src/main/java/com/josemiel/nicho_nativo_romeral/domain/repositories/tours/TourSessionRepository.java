package com.josemiel.nicho_nativo_romeral.domain.repositories.tours;

import com.josemiel.nicho_nativo_romeral.domain.entities.TourSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TourSessionRepository extends JpaRepository<TourSession, Integer> {
    /**
     * Encuentra las sesiones de tour para un tour específico en un rango de fechas.
     * @param tourId
     * @param from
     * @param to
     * @return
     */
    List<TourSession> findByTourIdAndStartAtBetween(Integer tourId, LocalDateTime from, LocalDateTime to);

    /**
     * Verifica si existe una sesión de tour para un tour específico en una fecha y hora.
     * @param tourId
     * @param startAt
     * @return
     */
    boolean existsByTourIdAndStartAt(Integer tourId, LocalDateTime startAt);
}
