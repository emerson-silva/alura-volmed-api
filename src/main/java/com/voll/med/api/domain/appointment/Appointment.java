package com.voll.med.api.domain.appointment;

import java.time.LocalDateTime;

import com.voll.med.api.domain.medico.Medico;
import com.voll.med.api.domain.paciente.Paciente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Appointment")
@Table(name = "appointments")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Paciente patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Medico doctor;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;
    @Column(name = "cancel_reason")
    @Enumerated(EnumType.STRING)
    private CancelReason cancelReason;
    public void cancel(CancelReason cancelReason) {
        this.cancelReason = cancelReason;
    }

}
