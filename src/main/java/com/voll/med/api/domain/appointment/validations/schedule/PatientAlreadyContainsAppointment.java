package com.voll.med.api.domain.appointment.validations.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;
import com.voll.med.api.domain.appointment.AppointmentRepository;

import lombok.Getter;

@Component
public class PatientAlreadyContainsAppointment implements BusinessRuleValidation {
    @Getter
    @Autowired
    AppointmentRepository repository;
    
    public void validate (AppointmentDataDTO data) {
        //Não permitir o agendamento de mais de uma consulta no mesmo dia para um mesmo paciente
        var initialDateTime = data.date().withHour(7);
        var finalDateTime = data.date().withHour(18);
        var isPatientBusyInDate = getRepository().existsByPatientIdAndDateBetween(data.patientId(), initialDateTime, finalDateTime);
        if (isPatientBusyInDate) {
            throw new BusinessRuleException("Paciente já possui uma consulta agendada nesse dia.");
        }
    }
}
