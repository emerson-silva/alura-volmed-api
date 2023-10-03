package com.voll.med.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record CancelAppointmentDTO(
    @NotNull
    Long appointmentId,
    @NotNull
    CancelReason cancelReason
) {
    //
}
