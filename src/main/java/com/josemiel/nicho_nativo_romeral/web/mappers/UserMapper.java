package com.josemiel.nicho_nativo_romeral.web.mappers;

import com.josemiel.nicho_nativo_romeral.domain.entities.User;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.MapStructCentralConfig;
import com.josemiel.nicho_nativo_romeral.web.dto.request.RegisterUserRequest;
import com.josemiel.nicho_nativo_romeral.web.dto.response.UserResponse;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface UserMapper {

    /**
     * Asigna una entidad User a un DTO UserResponse
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(source = "role.id", target = "roleId"),
            @Mapping(source = "role.name", target = "roleName")
    })
    UserResponse toResponse(User entity);

    /**
     * Asigna un DTO RegisterUserRequest a una entidad User
     * @param req
     * @return
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "role", ignore = true), // lo setea el servicio
            @Mapping(target = "passwordHash", ignore = true), // el servicio hashea
            @Mapping(target = "active", constant = "true")
    })
    User fromRegisterRequest(RegisterUserRequest req);

}
