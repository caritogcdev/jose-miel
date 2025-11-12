package com.josemiel.nicho_nativo_romeral.web.mappers;

import com.josemiel.nicho_nativo_romeral.domain.entities.Tour;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.MapStructCentralConfig;
import com.josemiel.nicho_nativo_romeral.web.dto.response.TourResponse;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface TourMapper {
    /**
     * Asigna una entidad Tour a un DTO TourResponse
     * @param entity
     * @return
     */
    TourResponse toResponse(Tour entity);
}
