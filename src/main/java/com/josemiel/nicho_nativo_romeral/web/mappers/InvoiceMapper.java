package com.josemiel.nicho_nativo_romeral.web.mappers;

import com.josemiel.nicho_nativo_romeral.domain.entities.Invoice;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.MapStructCentralConfig;
import com.josemiel.nicho_nativo_romeral.web.dto.response.InvoiceResponse;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface InvoiceMapper {

    /**
     * Asigna una entidad Invoice a un DTO InvoiceResponse
     * @param entity
     * @return
     */
    @Mapping(source = "booking.id", target = "bookingId")
    InvoiceResponse toResponse(Invoice entity);

}
