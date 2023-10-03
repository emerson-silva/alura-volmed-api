package com.voll.med.api.domain.appointment.validations.cancel;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.AppointmentRepository;
import com.voll.med.api.domain.appointment.CancelAppointmentDTO;

import lombok.Getter;

@Component
public class CancelInAdvance implements CancelRuleValidation {

    @Autowired
    @Getter
    private AppointmentRepository appointmentRepository;

    @Override
    public void validate(CancelAppointmentDTO cancelData) {
        var appointmentToCancel = getAppointmentRepository().getReferenceById(cancelData.appointmentId());
        var now = LocalDateTime.now();
        var timeInAdvance = Duration.between(now, appointmentToCancel.getDate()).toHours();
        if (timeInAdvance < 24) {
            throw new BusinessRuleException("Consultas só podem ser canceladas com 24 horas de antecedência.");
        }
    }
    
}
