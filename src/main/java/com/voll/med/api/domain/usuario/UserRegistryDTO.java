package com.voll.med.api.domain.usuario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRegistryDTO(
    @NotBlank
    String login,
    @NotBlank @Min(value = 8) @Max (16)
    String password
) {
    
}
