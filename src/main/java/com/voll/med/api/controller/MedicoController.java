package com.voll.med.api.controller;

import java.net.URI;
import java.util.Optional;

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

import com.voll.med.api.domain.medico.DadosAtualizarMedico;
import com.voll.med.api.domain.medico.DadosDetalhamentoMedico;
import com.voll.med.api.domain.medico.DadosListagemMedico;
import com.voll.med.api.domain.medico.DadosMedico;
import com.voll.med.api.domain.medico.MedicoRepository;
import com.voll.med.api.domain.medico.Medico;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrarMedico (@RequestBody @Valid DadosMedico dados, UriComponentsBuilder uriBuilder) {
        // Joga medico para variavel
        Medico medico = new Medico(dados);
        // Cria o medico no repositorio e atualiza o objeto medico
        getRepository().save(medico);

        // necessario calcular o URI a partir do UriComponentsBuilder, isso serve para retornar onde o dado criado pode ser consultado
        URI location = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        // Retorna um response do tipo created (201) com os dados do medico e onde consultar
        return ResponseEntity.created(location).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listarMedicos (@PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC ) Pageable page) {
        Page<DadosListagemMedico> response = getRepository().findByAtivo(Boolean.TRUE, page).map(DadosListagemMedico::new);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalharMedico (@PathVariable Long id) {
        Optional<Medico> medico = getRepository().findById(id);
        if (medico.isPresent()) {
            DadosDetalhamentoMedico response = new DadosDetalhamentoMedico(medico.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // pesquisar padrão de API Rest, pois acredito que o Método PUT deveria usar a URL do ID
    // Para seguir um padrão, pois no método GET é interessante usar o ID na URL
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizarMedico (@RequestBody @Valid DadosAtualizarMedico dados) {
        Medico medico = getRepository().getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        
        // 200
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> desabilitarMedico (@PathVariable Long id) {
        // Exclusao fisica deleteById(id)
        // Exclusao Logica
        var medico = getRepository().getReferenceById(id);
        medico.desativarMedico();
        // Return 204 - noContent
        return ResponseEntity.noContent().build();
    }
}
