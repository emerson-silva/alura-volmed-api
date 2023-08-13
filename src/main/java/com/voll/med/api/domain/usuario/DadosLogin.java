package com.voll.med.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(
    @NotBlank
    String login,
    @NotBlank
    String senha
) {
    //
}
