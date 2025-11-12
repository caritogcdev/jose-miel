package com.josemiel.nicho_nativo_romeral.web.dto.request;

import jakarta.validation.constraints.*;

public record CreateBookingRequest(
        @NotNull Integer userId,
        @NotNull Integer tourSessionId,
        @Min(1) Integer quantityVisitors) {

}
