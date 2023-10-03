package com.voll.med.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.voll.med.api.domain.appointment.Appointment;
import com.voll.med.api.domain.appointment.AppointmentCalendar;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;
import com.voll.med.api.domain.appointment.AppointmentDetailsDTO;
import com.voll.med.api.domain.appointment.AppointmentRepository;
import com.voll.med.api.domain.appointment.CancelAppointmentDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ApointmentController {

    @Autowired @Getter (value = AccessLevel.PRIVATE)
    AppointmentRepository appointmentRepository;

    @Autowired
    @Getter(value = AccessLevel.PRIVATE)
    AppointmentCalendar appointmentCalendar;

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentDetailsDTO> scheduleAppointment (@RequestBody @Valid AppointmentDataDTO appointmentData, UriComponentsBuilder builder) {
        var appointmentDetails = getAppointmentCalendar().scheduleAppointment(appointmentData);
        var uri = builder.path("/consultas/{id}").buildAndExpand(appointmentDetails.id());
        return ResponseEntity.created(uri.toUri()).body(appointmentDetails);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentDetailsDTO>> getAllAppointments (Pageable pageable) {
        Page<AppointmentDetailsDTO> appointments = getAppointmentRepository().findAll(pageable).map(AppointmentDetailsDTO::new);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetailsDTO> getAllAppointments (@PathVariable Long id) {
        AppointmentDetailsDTO appointment = new AppointmentDetailsDTO(getAppointmentRepository().getReferenceById(id));
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelAppointment(@Valid CancelAppointmentDTO cancelAppointmentDTO) {
        Appointment appointment = getAppointmentRepository().getReferenceById(cancelAppointmentDTO.appointmentId());
        appointment.cancel(cancelAppointmentDTO.cancelReason());
        return ResponseEntity.noContent().build();
    }
}
