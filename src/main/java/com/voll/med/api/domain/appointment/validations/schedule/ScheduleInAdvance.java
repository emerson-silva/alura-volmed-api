package com.voll.med.api.domain.appointment.validations.schedule;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;

@Component
public class ScheduleInAdvance implements BusinessRuleValidation {
    //"As consultas devem ser agendadas com antecedência mínima de 30 minutos"
    public void validate(AppointmentDataDTO data) {
        var minutesInAdvance = Duration.between(LocalDateTime.now(), data.date()).toMinutes();
        if (minutesInAdvance < 30) {
            throw new BusinessRuleException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
