package com.josemiel.nicho_nativo_romeral.web.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTourPricingTierRequest(
        @NotNull @Min(1) Integer minUser,
        Integer maxUser, // puede ser null = "en adelante"
        @NotNull BigDecimal pricePerPerson,
        @NotNull LocalDate validFrom,
        LocalDate validTo) {
}
