package com.josemiel.nicho_nativo_romeral.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Integer id,
        Integer invoiceId,
        String provider,
        String providerTxId,
        String status,
        BigDecimal amount,
        String authorizationCode,
        String errorCode,
        String errorMessage,
        LocalDateTime expiresAt) {

}
