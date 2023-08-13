package com.voll.med.api.domain.paciente;

public class DadosListagemPaciente {

    public Long id;
    public String nome;
    public String email;
    public String cpf;

    public DadosListagemPaciente (Paciente paciente){
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.email = paciente.getEmail();
        this.cpf = paciente.getCpf();
    }
}
