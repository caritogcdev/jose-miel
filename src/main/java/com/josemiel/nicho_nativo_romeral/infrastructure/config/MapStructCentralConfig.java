package com.josemiel.nicho_nativo_romeral.infrastructure.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValuePropertyMappingStrategy;

@MapperConfig( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MapStructCentralConfig {
    // Configuraciones globales para MapStruct
}
