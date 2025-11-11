package com.josemiel.nicho_nativo_romeral.web.dto.response;

public record UserResponse(
    Integer id,
    String firstName,
    String lastName,
    String email,
    String documentType,
    String docNumber,
    boolean active,
    Integer roleId,
    String roleName) {

}
