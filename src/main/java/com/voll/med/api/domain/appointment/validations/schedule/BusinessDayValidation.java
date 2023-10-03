package com.voll.med.api.domain.appointment.validations.schedule;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;

@Component
public class BusinessDayValidation implements BusinessRuleValidation {
    public void validate (AppointmentDataDTO data) {
        /*
         * O horário de funcionamento da clínica é de segunda a sábado, das 07:00 às 19:00
         * As consultas tem duração fixa de 1 hora
         */
        var isSunday = data.date().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var isOutOfBusinessHours = data.date().getHour() < 7 || data.date().getHour() > 18;

        if (isOutOfBusinessHours || isSunday) {
            throw new BusinessRuleException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
