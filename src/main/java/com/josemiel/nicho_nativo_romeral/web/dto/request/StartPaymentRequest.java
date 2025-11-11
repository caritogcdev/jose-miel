package com.josemiel.nicho_nativo_romeral.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record StartPaymentRequest(
        @NotNull Integer invoiceId) {

}
