package com.josemiel.nicho_nativo_romeral.web.dto.request;

import jakarta.validation.constraints.*;

public record CreateTourRequest(
        @NotBlank @Size(max = 60) String name,
        @Size(max = 255) String description,
        @NotNull @Min(1) Integer journeyDuration, // minutos
        @NotNull @Min(1) Integer usersPerTour, // cupo base
        Boolean active // opcional por si viene null, asumimos true
) {

}
