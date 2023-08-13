package com.voll.med.api.domain.medico;

import com.voll.med.api.domain.endereco.Endereco;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( of = "id" )
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;

    public Medico(DadosMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        // nao precisa setar a flag ativa pois o padrao do banco é ativo
    }

    public void atualizarInformacoes(DadosAtualizarMedico dados) {
        if (dados.nome()!=null) {
            this.nome = dados.nome();
        }
        if (dados.telefone()!=null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco()!=null) {
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

    public void desativarMedico() {
        this.ativo = Boolean.FALSE;
    }

    public void ativarMedico() {
        this.ativo = Boolean.TRUE;
    }
}