package com.voll.med.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.voll.med.api.domain.paciente.DadosAtualizarPaciente;
import com.voll.med.api.domain.paciente.DadosDetalhamentoPaciente;
import com.voll.med.api.domain.paciente.DadosListagemPaciente;
import com.voll.med.api.domain.paciente.DadosPaciente;
import com.voll.med.api.domain.paciente.PacienteRepository;
import com.voll.med.api.domain.paciente.Paciente;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar (@RequestBody @Valid DadosPaciente dados, UriComponentsBuilder uriBuilder) {
        // Criar paciente
        Paciente paciente = new Paciente(dados);
        getRepository().save(paciente);

        // Calcular o path e converter para URI
        URI location = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        // Montar o response com o URI, adicionar o conteudo no body
        return ResponseEntity.created(location).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes (@PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable pageable) {
        // Tamanho pode ser explorado, eh interessante validar os parametros
        Page<DadosListagemPaciente> responseContent = getRepository().findByAtivo(Boolean.TRUE, pageable).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(responseContent);
    }

    // Atualizar Paciente: nome, telefone, endereço
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizarPaciente (@RequestBody @Valid DadosAtualizarPaciente dados) {
        var paciente = getRepository().getReferenceById(dados.id());
        paciente.atualizarPaciente(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> desativarPaciente(@PathVariable Long id) {
        var paciente = getRepository().getReferenceById(id);
        paciente.desativarPaciente();
        return ResponseEntity.noContent().build();
    }
}
