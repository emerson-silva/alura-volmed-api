package com.voll.med.api.domain.appointment.validations.cancel;

import com.voll.med.api.domain.appointment.CancelAppointmentDTO;

public interface CancelRuleValidation {
    void validate(CancelAppointmentDTO cancelData);
}
