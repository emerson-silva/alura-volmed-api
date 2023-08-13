package com.voll.med.api.domain.medico;

public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DadosListagemMedico (Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}

// public class DadosListagemMedico {
//     public Long id;
//     public String nome;
//     public String email;
//     public String crm;
//     public Especialidade especialidade;

//     public DadosListagemMedico(Medico medico) {
//         this.id = medico.getId();
//         this.nome = medico.getNome();
//         this.email = medico.getEmail();
//         this.crm = medico.getCrm();
//         this.especialidade = medico.getEspecialidade();
//     }
// }
