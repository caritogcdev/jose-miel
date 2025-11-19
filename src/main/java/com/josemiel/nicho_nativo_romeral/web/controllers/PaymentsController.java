package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.app.services.PaymentService;
import com.josemiel.nicho_nativo_romeral.domain.enums.PaymentStatus;
import com.josemiel.nicho_nativo_romeral.web.dto.request.StartPaymentRequest;
import com.josemiel.nicho_nativo_romeral.web.dto.response.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentsController {
    private final PaymentService paymentService;

    @PostMapping("/start")
    public ResponseEntity<PaymentResponse> start(@Valid @RequestBody StartPaymentRequest req) {
        return ResponseEntity.ok(paymentService.start(req.invoiceId()));
    }

    // “Webhook” simulado: el front/postman puede llamarlo manualmente para cambiar
    // el estado
    public record SimulatedCallbackRequest(String providerTxId, String state,
            String authorizationCode, String errorCode, String errorMessage) {
    }

    @PostMapping("/simulated-callback")
    public ResponseEntity<PaymentResponse> callback(@RequestBody SimulatedCallbackRequest body) {
        PaymentStatus status = PaymentStatus.valueOf(body.state().toUpperCase());
        return ResponseEntity.ok(
                paymentService.applyCallback(
                        body.providerTxId(),
                        status,
                        body.authorizationCode(),
                        body.errorCode(),
                        body.errorMessage()));
    }
}
