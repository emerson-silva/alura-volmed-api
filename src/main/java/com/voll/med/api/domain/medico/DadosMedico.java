package com.voll.med.api.domain.medico;

import com.voll.med.api.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosMedico(
    @NotBlank
    String nome,
    @NotBlank
    @Email
    String email,
    @NotBlank
    // Exemplo de mensagem customizada de validação
    @Pattern(regexp = "\\d{10,11}", message = "{medico.validation.telefone.invalidFormat}")
    String telefone,
    @NotNull
    @Pattern(regexp = "\\d{4,6}")
    String crm,
    @NotNull
    Especialidade especialidade,
    @NotNull @Valid
    DadosEndereco endereco) {

}
