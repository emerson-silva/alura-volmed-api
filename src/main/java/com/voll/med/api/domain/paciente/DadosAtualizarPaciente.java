package com.voll.med.api.domain.paciente;

import com.voll.med.api.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizarPaciente(
    @NotNull
    Long id,
    String nome,
    @Pattern(regexp = "\\d{10,11}")
    String telefone,
    @Valid
    DadosEndereco endereco
) {

}
