package com.voll.med.api.domain.usuario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
    Long id,
    @NotBlank
    @Min(8) @Max(16)
    String password,
    @NotBlank
    @Min(8) @Max(16)
    String passwordConfirmation) {
}
