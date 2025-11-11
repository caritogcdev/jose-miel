package com.josemiel.nicho_nativo_romeral.domain.repositories.bookings;

import com.josemiel.nicho_nativo_romeral.domain.entities.Booking;
import com.josemiel.nicho_nativo_romeral.domain.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    /**
     * Encuentra las reservas de cupos para una sesión de tour específica y un conjunto de estados de reserva.
     * @param sessionId
     * @param statuses
     * @return
     */

    @Query("""
               SELECT COALESCE(SUM(b.quantityVisitors), 0)
               FROM Booking b
               WHERE b.tourSession.id = :sessionId
                 AND b.status IN :statuses
                 AND (b.lockedUntil IS NULL OR b.lockedUntil > CURRENT_TIMESTAMP)
            """)
    int reservedSeats(Integer sessionId, Collection<BookingStatus> statuses);
}
