package com.voll.med.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findByAtivo(boolean i, Pageable page);

    @Query("""
        SELECT m FROM Medico m
        WHERE m.ativo = TRUE
            AND m.especialidade = :expertise
            AND m.id NOT IN (
                SELECT c.doctor.id FROM Appointment c
                WHERE
                    c.date = :dateParam
                    AND c.cancelReason IS NULL
            )
        ORDER BY rand()
        LIMIT 1
    """
    )
    Medico getRandomDoctor(Especialidade expertise, @Param("dateParam")LocalDateTime dateParam);

    @Query("""
            SELECT m.ativo FROM Medico m WHERE m.id = :id
            """)
    boolean findEnabledById(Long id);
    
}
