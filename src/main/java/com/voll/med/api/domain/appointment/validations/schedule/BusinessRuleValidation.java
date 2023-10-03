package com.voll.med.api.domain.appointment.validations.schedule;

import com.voll.med.api.domain.appointment.AppointmentDataDTO;

public interface BusinessRuleValidation {
    void validate (AppointmentDataDTO data);
}
