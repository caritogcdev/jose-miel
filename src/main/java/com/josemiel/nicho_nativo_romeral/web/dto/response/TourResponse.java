package com.josemiel.nicho_nativo_romeral.web.dto.response;

public record TourResponse(
        Integer id,
        String name,
        String description,
        Integer journeyDuration,
        Integer usersPerTour,
        boolean active) {

}
