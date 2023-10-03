package com.voll.med.api.domain.paciente;

import org.apache.commons.lang3.StringUtils;

import com.voll.med.api.domain.endereco.Endereco;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name="Paciente")
@Table(name="pacientes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    String email;
    String telefone;
    String cpf;
    @Embedded
    Endereco endereco;
    private boolean ativo;

    public Paciente (DadosPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
        this.ativo = Boolean.TRUE;
    }

    public void atualizarPaciente(DadosAtualizarPaciente dados) {
        if (StringUtils.isNotBlank(dados.nome())) {
            this.nome = dados.nome();
        }
        if (StringUtils.isNotBlank(dados.telefone())) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

    public void desativarPaciente() {
        this.ativo = Boolean.FALSE;
    }
}
