package com.josemiel.nicho_nativo_romeral.web.mappers;

import com.josemiel.nicho_nativo_romeral.domain.entities.TourPricingTier;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.MapStructCentralConfig;
import com.josemiel.nicho_nativo_romeral.web.dto.response.TourPricingTierResponse;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface TourPricingTierMapper {
    /**
     * Asigna una entidad TourPricingTier a un DTO TourPricingTierResponse
     * @param entity
     * @return
     */
    @Mapping(source = "tour.id", target = "tourId")
    TourPricingTierResponse toResponse(TourPricingTier entity);
}
