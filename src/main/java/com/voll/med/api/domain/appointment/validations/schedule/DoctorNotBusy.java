package com.voll.med.api.domain.appointment.validations.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;
import com.voll.med.api.domain.appointment.AppointmentRepository;

import lombok.Getter;

@Component
public class DoctorNotBusy implements BusinessRuleValidation {
    
    @Getter
    @Autowired
    AppointmentRepository repository;

    public void validate (AppointmentDataDTO data) {
        //Não permitir o agendamento de uma consulta com um médico que já possui outra consulta agendada na mesma data/hora
        var doctorIsBusyInDate = getRepository().existsByDoctorIdAndDateAndCancelReasonIsNull(data.doctorId(), data.date());
        if (doctorIsBusyInDate) {
            throw new BusinessRuleException("Médico já possui uma consulta agendada nesse horário.");
        }
    }
}
