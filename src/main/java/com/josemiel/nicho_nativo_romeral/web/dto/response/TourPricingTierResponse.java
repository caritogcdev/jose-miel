package com.josemiel.nicho_nativo_romeral.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TourPricingTierResponse(
        Integer id,
        Integer tourId,
        Integer minUser,
        Integer maxUser,
        BigDecimal pricePerPerson,
        LocalDate validFrom,
        LocalDate validTo) {

}