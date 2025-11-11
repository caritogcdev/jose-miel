package com.josemiel.nicho_nativo_romeral.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingResponse(
        Integer id,
        Integer userId,
        Integer tourSessionId,
        Integer quantityVisitors,
        String status,
        BigDecimal totalAmount,
        LocalDateTime lockedUntil) {

}
