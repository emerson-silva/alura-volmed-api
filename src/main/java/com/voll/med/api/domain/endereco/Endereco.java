package com.voll.med.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
    }

    public void atualizarEndereco(DadosEndereco dadosEndereco) {
        if (this.logradouro!=null) {
            this.logradouro = dadosEndereco.logradouro();
        }
        if (this.bairro!=null) {
            this.bairro = dadosEndereco.bairro();
        }
        if (this.cep!=null) {
            this.cep = dadosEndereco.cep();
        }
        if (this.numero!=null) {
            this.numero = dadosEndereco.numero();
        }
        if (this.complemento!=null) {
            this.complemento = dadosEndereco.complemento();
        }
        if (this.cidade!=null) {
            this.cidade = dadosEndereco.cidade();
        }
        if (this.uf!=null) {
            this.uf = dadosEndereco.uf();
        }
    }
}
