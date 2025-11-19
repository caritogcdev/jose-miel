package com.josemiel.nicho_nativo_romeral.web.dto.request;

import jakarta.validation.constraints.*;

public record UpdateTourRequest(
        @NotBlank @Size(max = 60) String name,
        @Size(max = 255) String description,
        @NotNull @Min(1) Integer journeyDuration,
        @NotNull @Min(1) Integer usersPerTour,
        @NotNull Boolean active) {
}
