package com.josemiel.nicho_nativo_romeral.web.dto.request;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

public record CreateTourSessionRequest(
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt,
        @NotNull @Min(1) Integer capacity) {

}
