package com.josemiel.nicho_nativo_romeral.web.dto.response;

import java.math.BigDecimal;

public record InvoiceResponse(Integer id,
        Integer bookingId,
        String consecutive,
        BigDecimal subtotal,
        BigDecimal total) {

}
