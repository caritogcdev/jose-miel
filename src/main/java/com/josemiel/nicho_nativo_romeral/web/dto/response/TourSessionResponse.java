package com.josemiel.nicho_nativo_romeral.web.dto.response;

import java.time.LocalDateTime;

public record TourSessionResponse(
        Integer id,
        Integer tourId,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Integer capacity,
        String status) {

}
