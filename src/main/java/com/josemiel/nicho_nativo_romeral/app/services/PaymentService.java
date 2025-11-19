package com.josemiel.nicho_nativo_romeral.app.services;

import com.josemiel.nicho_nativo_romeral.domain.entities.Booking;
import com.josemiel.nicho_nativo_romeral.domain.entities.Invoice;
import com.josemiel.nicho_nativo_romeral.domain.entities.Payment;
import com.josemiel.nicho_nativo_romeral.domain.enums.BookingStatus;
import com.josemiel.nicho_nativo_romeral.domain.enums.PaymentProvider;
import com.josemiel.nicho_nativo_romeral.domain.enums.PaymentStatus;
import com.josemiel.nicho_nativo_romeral.domain.repositories.bookings.BookingRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.invoices.InvoiceRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.payments.PaymentRepository;
import com.josemiel.nicho_nativo_romeral.web.dto.response.PaymentResponse;
import com.josemiel.nicho_nativo_romeral.web.mappers.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final InvoiceRepository invoiceRepo;
    private final BookingRepository bookingRepo;
    private final PaymentMapper paymentMapper;

    /**
     * Crea un pago PENDING, fija expiresAt y bloquea la reserva (lockedUntil).
     */
    @Transactional
    public PaymentResponse start(Integer invoiceId) {
        Invoice inv = invoiceRepo.findById(invoiceId).orElseThrow();
        Payment pay = Payment.builder()
                .invoice(inv)
                .status(PaymentStatus.PENDING)
                .provider(PaymentProvider.PAYU) // simulado
                .amount(inv.getTotal())
                .providerTxId(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        pay = paymentRepo.save(pay);

        Booking booking = inv.getBooking();
        booking.setStatus(BookingStatus.PENDING_PAYMENT);
        booking.setLockedUntil(pay.getExpiresAt());
        bookingRepo.save(booking);

        return paymentMapper.toResponse(pay);
    }

    /**
     * “Webhook” simulado: aplica el resultado del proveedor
     * (APPROVED/DECLINED/ERROR).
     */
    @Transactional
    public PaymentResponse applyCallback(String providerTxId,
            PaymentStatus status,
            String authCode,
            String errCode,
            String errMsg) {
        Payment pay = paymentRepo.findByProviderTxId(providerTxId).orElseThrow();
        Booking booking = pay.getInvoice().getBooking();

        switch (status) {
            case APPROVED -> {
                pay.setStatus(PaymentStatus.APPROVED);
                pay.setAuthorizationCode(authCode);
                booking.setStatus(BookingStatus.CONFIRMED);
                booking.setLockedUntil(null);
            }
            case DECLINED, ERROR -> {
                pay.setStatus(status);
                pay.setErrorCode(errCode);
                pay.setErrorMessage(errMsg);
                booking.setLockedUntil(null);
                booking.setStatus(BookingStatus.DRAFT); // reintento posible
            }
            default -> {
                /* PENDING: sin cambios */ }
        }

        paymentRepo.save(pay);
        bookingRepo.save(booking);
        return paymentMapper.toResponse(pay);
    }

    /**
     * Expira pagos PENDING cuyo expiresAt ya pasó y libera los locks de reservas.
     * Corre cada minuto.
     */
    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void expirePendings() {
        LocalDateTime now = LocalDateTime.now();
        var toExpire = paymentRepo.findByStatusAndExpiresAtBefore(PaymentStatus.PENDING, now);

        for (Payment p : toExpire) {
            p.setStatus(PaymentStatus.EXPIRED);
            Booking b = p.getInvoice().getBooking();
            if (b.getStatus() == BookingStatus.PENDING_PAYMENT) {
                b.setStatus(BookingStatus.DRAFT);
                b.setLockedUntil(null);
            }
        }
        paymentRepo.saveAll(toExpire);
    }

}