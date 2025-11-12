package com.josemiel.nicho_nativo_romeral.web.mappers;

import com.josemiel.nicho_nativo_romeral.domain.entities.Booking;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.MapStructCentralConfig;
import com.josemiel.nicho_nativo_romeral.web.dto.request.CreateBookingRequest;
import com.josemiel.nicho_nativo_romeral.web.dto.response.BookingResponse;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface BookingMapper {
    // Métodos de mapeo para Booking
    /**
     * Asigna una entidad Booking a un DTO BookingResponse
     * @param entity la entidad Booking a mapear
     * @return el DTO BookingResponse mapeado
     */

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "tourSession.id", target = "tourSessionId"),
            @Mapping(source = "status", target = "status")
    })
    BookingResponse toResponse(Booking entity);

    /**
     * Asigna un DTO CreateBookingRequest a una entidad Booking
     * @param req el DTO CreateBookingRequest a mapear
     * @return la entidad Booking mapeada
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "tourSession", ignore = true),
            @Mapping(source = "quantityVisitors", target = "quantityVisitors"),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "totalAmount", ignore = true),
            @Mapping(target = "lockedUntil", ignore = true)
    })
    Booking fromCreateRequest(CreateBookingRequest req);

}
