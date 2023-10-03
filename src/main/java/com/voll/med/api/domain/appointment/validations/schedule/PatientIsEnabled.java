package com.voll.med.api.domain.appointment.validations.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;
import com.voll.med.api.domain.paciente.PacienteRepository;

import lombok.Getter;

@Component
public class PatientIsEnabled implements BusinessRuleValidation {
    @Getter
    @Autowired
    PacienteRepository repository;
    
    public void validate(AppointmentDataDTO data) {
        //Não permitir o agendamento de consultas com pacientes inativos no sistema
        var isPatientEnabled = getRepository().findEnabledById(data.patientId());
        if (!isPatientEnabled) {
            throw new BusinessRuleException("Consulta n\u00E3o pode ser agendada com Paciente exclu\u00EDdo");
        }
    }
}
