package com.voll.med.api.domain.appointment;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    boolean existsByDoctorIdAndDateAndCancelReasonIsNull(Long doctorId, LocalDateTime date);

    boolean existsByPatientIdAndDateBetween(Long patientId, LocalDateTime initialDateTime, LocalDateTime finalDateTime);
    
}
