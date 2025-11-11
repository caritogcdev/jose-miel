package com.josemiel.nicho_nativo_romeral.domain.repositories.tours;

import org.springframework.data.jpa.repository.JpaRepository;
import com.josemiel.nicho_nativo_romeral.domain.entities.TourPricingTier;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional; // Ayuda a evitar los errores de NullPointerException 

public interface TourPricingTierRepository extends JpaRepository<TourPricingTier, Integer> {
    // List<TourPricingTier> findByTourId(Integer tourId);

    /**
     * Encuentra un nivel de precios para un tour específico, número de pasajeros y fecha.
     * @param tourId
     * @param pax
     * @param onDate
     * @return
     */
    @Query("""
               SELECT tpt
               FROM TourPricingTier tpt
               WHERE tpt.tour.id = :tourId
                 AND tpt.minUser <= :pax
                 AND (tpt.maxUser IS NULL OR :pax <= tpt.maxUser)
                 AND tpt.validFrom <= :onDate
                 AND (tpt.validTo IS NULL OR :onDate <= tpt.validTo)
               ORDER BY tpt.minUser DESC
            """)
    Optional<TourPricingTier> findTierFor(Integer tourId, Integer pax, LocalDate onDate);
}
