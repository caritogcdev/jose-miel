package com.josemiel.nicho_nativo_romeral.web.dto.request;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public record UpdateTourSessionRequest(
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt,
        @NotNull @Min(1) Integer capacity,
        @NotNull String status // "OPEN", "FULL", "CLOSED"
) {
}