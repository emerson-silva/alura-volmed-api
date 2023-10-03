package com.voll.med.api.domain.appointment.validations.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;
import com.voll.med.api.domain.medico.MedicoRepository;

import lombok.Getter;

@Component
public class DoctorIsEnabled implements BusinessRuleValidation {
    @Getter
    @Autowired
    MedicoRepository repository;

    public void validate (AppointmentDataDTO data) {
        //Não permitir o agendamento de consultas com médicos inativos no sistema
        if (data.doctorId()==null) {
            return;
        }

        var isEnableById = getRepository().findEnabledById(data.doctorId());
        if (!isEnableById) {
            throw new BusinessRuleException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
