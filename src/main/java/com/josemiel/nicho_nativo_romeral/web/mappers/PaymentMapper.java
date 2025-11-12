package com.josemiel.nicho_nativo_romeral.web.mappers;

import com.josemiel.nicho_nativo_romeral.domain.entities.Payment;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.MapStructCentralConfig;
import com.josemiel.nicho_nativo_romeral.web.dto.response.PaymentResponse;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface PaymentMapper {
    // Métodos de mapeo para Payment

    /**
     * Asigna una entidad Payment a un DTO PaymentResponse
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(source = "invoice.id", target = "invoiceId"),
            @Mapping(source = "provider", target = "provider"),
            @Mapping(source = "status", target = "status")
    })
    PaymentResponse toResponse(Payment entity);

}
