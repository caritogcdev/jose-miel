package com.josemiel.nicho_nativo_romeral.domain.repositories.bookings;

import com.josemiel.nicho_nativo_romeral.domain.entities.Booking;
import com.josemiel.nicho_nativo_romeral.domain.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

  /**
   * Encuentra las reservas de cupos para una sesión de tour específica y un
   * conjunto de estados de reserva.
   * 
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

  /**
   * Encuentra todas las reservas asociadas al email de un usuario.
   * 
   * @param email
   * @return
   */
  List<Booking> findByUser_Email(String email);

  /**
   * Encuentra una reserva por su ID y el email del usuario asociado.
   * 
   * @param id
   * @param email
   * @return
   */
  Optional<Booking> findByIdAndUser_Email(Integer id, String email);

}
