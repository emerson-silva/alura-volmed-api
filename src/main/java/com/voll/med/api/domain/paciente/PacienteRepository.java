package com.voll.med.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    Page<Paciente> findByAtivo(Boolean ativo, Pageable pageable);

    @Query("""
            SELECT p.ativo FROM Paciente p WHERE p.id = :id AND p.ativo = TRUE
            """)
    boolean findEnabledById(Long id);

}
