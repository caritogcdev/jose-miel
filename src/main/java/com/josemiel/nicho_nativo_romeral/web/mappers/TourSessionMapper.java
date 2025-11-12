package com.josemiel.nicho_nativo_romeral.web.mappers;

import com.josemiel.nicho_nativo_romeral.domain.entities.TourSession;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.MapStructCentralConfig;
import com.josemiel.nicho_nativo_romeral.web.dto.response.TourSessionResponse;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface TourSessionMapper {
    /**
     * Asigna una entidad TourSession a un DTO TourSessionResponse
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(source = "tour.id", target = "tourId"),
            @Mapping(source = "status", target = "status")
    })
    TourSessionResponse toResponse(TourSession entity);
}
