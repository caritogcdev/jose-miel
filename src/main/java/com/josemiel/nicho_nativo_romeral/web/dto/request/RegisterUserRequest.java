package com.josemiel.nicho_nativo_romeral.web.dto.request;

import jakarta.validation.constraints.*;

public record RegisterUserRequest(
        @NotBlank @Size(max = 50) String firstName,
        @NotBlank @Size(max = 50) String lastName,
        @NotBlank @Email @Size(max = 60) String email,
        @NotBlank @Size(min = 10, max = 300) String password,
        @NotBlank @Size(max = 50) String documentType,
        @NotBlank @Size(max = 20) String docNumber,
        @NotNull Integer roleId) {
}
