package com.voll.med.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
    @NotNull
    Long id,
    @NotBlank
    @Size(min = 8, max = 16)
    String password,
    @NotBlank
    @Size(min = 8, max = 16)
    String passwordConfirmation) {
}
