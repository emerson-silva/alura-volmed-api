package com.voll.med.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistryDTO(
    @NotBlank
    String login,
    @NotBlank @Size(min = 8, max = 16)
    String password
) {
    public UserRegistryDTO withUpdatedPassword(String newPasswordHash) {
        return new UserRegistryDTO(login, newPasswordHash);
    }
}
