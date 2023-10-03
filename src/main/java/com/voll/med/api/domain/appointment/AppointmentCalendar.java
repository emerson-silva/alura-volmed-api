package com.voll.med.api.domain.appointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voll.med.api.domain.BusinessRuleException;
import com.voll.med.api.domain.appointment.validations.cancel.CancelRuleValidation;
import com.voll.med.api.domain.appointment.validations.schedule.BusinessRuleValidation;
import com.voll.med.api.domain.medico.Medico;
import com.voll.med.api.domain.medico.MedicoRepository;
import com.voll.med.api.domain.paciente.PacienteRepository;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class AppointmentCalendar {

    @Autowired
    @Getter(value = AccessLevel.PRIVATE)
    AppointmentRepository appointmentRepository;

    @Autowired
    @Getter(value = AccessLevel.PRIVATE)
    PacienteRepository patientRepository;

    @Autowired
    @Getter(value = AccessLevel.PRIVATE)
    MedicoRepository doctorRepository;

    @Autowired
    List<BusinessRuleValidation> scheduleValidations;

    @Autowired
    List<CancelRuleValidation> cancelValidations;

    public AppointmentDetailsDTO scheduleAppointment(AppointmentDataDTO appointmentData) {
        scheduleValidations.forEach(v -> v.validate(appointmentData));

        var patientOptional = getPatientRepository().findById(appointmentData.patientId());
        if (!patientOptional.isPresent()) {
            throw new BusinessRuleException("Paciente não encontrado");
        }

        var doctor = calculateDoctor(appointmentData);
        if (doctor==null) {
            throw new BusinessRuleException("Não existe médico disponível nessa data.");
        }
        var appointment = new Appointment(null, patientOptional.get(), doctor, appointmentData.date(), null);

        getAppointmentRepository().save(appointment);
        return new AppointmentDetailsDTO(appointment);
    }

    private Medico calculateDoctor (AppointmentDataDTO appointmentData) {
        if (appointmentData.doctorId()!=null) {
            var doctorOptional = getDoctorRepository().findById(appointmentData.doctorId());
            if (doctorOptional.isPresent()) {
                return doctorOptional.get();
            }
        } else if (appointmentData.expertise()!=null) {
            var doctor = getDoctorRepository().getRandomDoctor(appointmentData.expertise(), appointmentData.date());
            if (doctor!=null) {
                return doctor;
            }
        }

        throw new BusinessRuleException("Medico não encontrado"); 
    }

    public Appointment cancelAppointment (CancelAppointmentDTO cancelData) {
        if (!getAppointmentRepository().existsById(cancelData.appointmentId())) {
            throw new BusinessRuleException("Consulta não encontrada");
        }

        cancelValidations.forEach(v -> v.validate(cancelData));

        var appointment = getAppointmentRepository().getReferenceById(cancelData.appointmentId());
        appointment.cancel(cancelData.cancelReason());

        getAppointmentRepository().save(appointment);
        return appointment;
    }
}
