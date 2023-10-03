package com.voll.med.api.domain.appointment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.voll.med.api.domain.medico.Especialidade;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record AppointmentDataDTO(
    @JsonAlias("idPaciente")
    @NotNull
    Long patientId,
    @JsonAlias("idMedico")
    Long doctorId,
    @JsonAlias("especialidade")
    Especialidade expertise,
    @NotNull
    @JsonAlias("data")
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime date
) {
    @AssertTrue(message = "\u00C9 necess\u00E1rio fornecer pelo menos um dos campos: 'idMedico' ou 'especialidade'.")
    private boolean isDoctorOrExpertiseNotNull() {
        return doctorId != null || expertise != null;
    }
}
