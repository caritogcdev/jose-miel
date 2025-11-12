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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Transactional
    public BookingResponse create(CreateBookingRequest req) {
        User user = userRepo.findById(req.userId()).orElseThrow();
        TourSession session = sessionRepo.findById(req.tourSessionId()).orElseThrow();

        int pax = req.quantityVisitors();
        // Validación de capacidad (CONFIRMED + PENDING con lock vigente)
        var seats = bookingRepo.reservedSeats(session.getId(), java.util.List.of(CONFIRMED, PENDING_PAYMENT));
        int available = session.getCapacity() - Math.toIntExact(seats);
        if (pax > available)
            throw new IllegalStateException("No hay cupos suficientes");

        // Precio según tier válido hoy
        var tier = tierRepo.findTierFor(session.getTour().getId(), pax, LocalDate.now())
                .orElseThrow(() -> new IllegalStateException("No hay tarifa configurada para " + pax + " pax"));
        BigDecimal total = tier.getPricePerPerson().multiply(BigDecimal.valueOf(pax));

        // Crear booking DRAFT
        Booking b = mapper.fromCreateRequest(req);
        b.setUser(user);
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
}
