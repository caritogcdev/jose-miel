package com.josemiel.nicho_nativo_romeral.app.services;

import com.josemiel.nicho_nativo_romeral.domain.entities.*;
import com.josemiel.nicho_nativo_romeral.domain.enums.BookingStatus;
import com.josemiel.nicho_nativo_romeral.domain.repositories.bookings.BookingRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.invoices.InvoiceRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.tours.TourPricingTierRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.tours.TourSessionRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.users.UserRepository;
import com.josemiel.nicho_nativo_romeral.web.dto.request.CreateBookingRequest;
import com.josemiel.nicho_nativo_romeral.web.dto.response.BookingResponse;
import com.josemiel.nicho_nativo_romeral.web.mappers.BookingMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.josemiel.nicho_nativo_romeral.domain.enums.BookingStatus.*;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    private final TourSessionRepository sessionRepo;
    private final TourPricingTierRepository tierRepo;
    private final InvoiceRepository invoiceRepo;
    private final BookingMapper mapper;
    private final MailService mailService;

    // ===================== CREAR RESERVA (USER) ===================== //

    /**
     * Crea una reserva para el usuario autenticado (se ignora cualquier userId que
     * venga en el body por seguridad).
     */
    @Transactional
    public BookingResponse create(CreateBookingRequest req, String userEmail) {
        // Usuario autenticado
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Sesión de tour
        TourSession session = sessionRepo.findById(req.tourSessionId())
                .orElseThrow(() -> new EntityNotFoundException("Sesión de tour no encontrada"));

        int pax = req.quantityVisitors();

        // Validación de capacidad (CONFIRMED + PENDING_PAYMENT con lock vigente)
        int seats = bookingRepo.reservedSeats(session.getId(), List.of(CONFIRMED, PENDING_PAYMENT));
        int available = session.getCapacity() - seats;
        if (pax > available) {
            throw new IllegalStateException("No hay cupos suficientes");
        }

        // Precio según tier válido hoy
        var tier = tierRepo.findTierFor(session.getTour().getId(), pax, LocalDate.now())
                .orElseThrow(() -> new IllegalStateException("No hay tarifa configurada para " + pax + " pax"));
        BigDecimal total = tier.getPricePerPerson().multiply(BigDecimal.valueOf(pax));

        // Crear booking DRAFT
        Booking b = mapper.fromCreateRequest(req);
        b.setUser(user); // 🔐 dueño = usuario autenticado
        b.setTourSession(session);
        b.setStatus(BookingStatus.DRAFT);
        b.setTotalAmount(total);
        b = bookingRepo.save(b);

        // Generar factura (simple: subtotal=total, total=total)
        Invoice inv = Invoice.builder()
                .booking(b)
                .consecutive("INV-" + System.currentTimeMillis())
                .subtotal(total)
                .total(total)
                .build();
        invoiceRepo.save(inv);

        mailService.sendBookingCreated(b);
        return mapper.toResponse(b);
    }

    // ===================== USER: VER SOLO SUS RESERVAS ===================== //

    /**
     * USER: obtener una reserva por id, verificando que sea del usuario.
     */
    @Transactional(readOnly = true)
    public BookingResponse getMyBooking(Integer bookingId, String userEmail) {
        Booking b = bookingRepo.findByIdAndUser_Email(bookingId, userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada o no te pertenece"));
        return mapper.toResponse(b);
    }

    /**
     * USER: listar todas mis reservas.
     */
    @Transactional(readOnly = true)
    public List<BookingResponse> listMyBookings(String userEmail) {
        return bookingRepo.findByUser_Email(userEmail)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ===================== ADMIN: VER TODAS ===================== //

    /**
     * ADMIN: obtener cualquier reserva por id.
     */
    @Transactional(readOnly = true)
    public BookingResponse getByIdAdmin(Integer bookingId) {
        Booking b = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
        return mapper.toResponse(b);
    }

    /**
     * ADMIN: listar todas las reservas.
     */
    @Transactional(readOnly = true)
    public List<BookingResponse> listAllAdmin() {
        return bookingRepo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
