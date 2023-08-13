package com.voll.med.api.domain.medico;

import com.voll.med.api.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMedico(
    @NotNull
    Long id,
    String nome,
    String telefone,
    @Valid DadosEndereco endereco) {

}