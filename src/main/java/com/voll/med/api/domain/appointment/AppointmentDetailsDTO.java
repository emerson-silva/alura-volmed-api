package com.voll.med.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetailsDTO(
    Long id,
    Long patientId,
    Long doctorId,
    LocalDateTime date
) {

    public AppointmentDetailsDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getPatient().getId(), appointment.getDoctor().getId(), appointment.getDate());
    }

}
