package com.voll.med.api.domain.medico;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.voll.med.api.domain.appointment.Appointment;
import com.voll.med.api.domain.endereco.DadosEndereco;
import com.voll.med.api.domain.paciente.DadosPaciente;
import com.voll.med.api.domain.paciente.Paciente;

import lombok.AccessLevel;
import lombok.Getter;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTests {
    @Autowired
    @Getter(value = AccessLevel.PRIVATE)
    TestEntityManager entityManager;
    @Getter(value = AccessLevel.PRIVATE)
    @Autowired
    private MedicoRepository doctorRepository;

    private static final String DEFAULT_PHONE_FOR_TESTING = "1150443345";

    @Test
    @DisplayName("Deve devolver null quando o unico Cardiologista cadastrado nao estiver disponivel na data")
    void getRandomDoctorScenario1() {
        var doctor = createDoctor("Busy Doctor", "busy.doctor@volmed.com", "012346", Especialidade.CARDIOLOGIA);
        var patient = createPatient("Paciente Teste", "teste@paciente.com", "00912365411");

        var nextMonday10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var appointment = createAppointment(doctor, patient, nextMonday10);
        System.out.println("CancelReason = " + appointment.getCancelReason());
        System.out.println("Appointment.Date = " + appointment.getDate());
        System.out.println("NextMonday = " + nextMonday10);

        Medico returnedDoctor = getDoctorRepository().getRandomDoctor(Especialidade.CARDIOLOGIA, nextMonday10);
        Assertions.assertThat(returnedDoctor).isNull();
    }

    @Test
    @DisplayName("Deve retornar um medico quando existir algum disponível na data")
    void getRandomDoctorScenario2() {
        createDoctor("Free Doctor", "free.doctor@volmed.com", "123765", Especialidade.ORTOPEDIA);
        var nextTuesday11 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(11, 0);
        var availableDoctor = getDoctorRepository().getRandomDoctor(Especialidade.ORTOPEDIA, nextTuesday11);
        Assertions.assertThat(availableDoctor).isNotNull();
    }

    private Appointment createAppointment(Medico doctor, Paciente patient, LocalDateTime date) {
        var appointment = new Appointment(null, patient, doctor, date, null);
        getEntityManager().persist(appointment);
        return appointment;
    }

    private Medico createDoctor(String name, String email, String crm, Especialidade expertise) {
        var doctor = new Medico(mockDoctorData(name, email, crm, expertise));
        getEntityManager().persist(doctor);
        return doctor;
    }

    private Paciente createPatient(String name, String email, String document) {
        var patient = new Paciente(mockPatientData(name, email, document));
        
        entityManager.persist(patient);
        return patient;
    }

    private DadosMedico mockDoctorData(String name, String email, String crm, Especialidade expertise) {
        return new DadosMedico(name, email, DEFAULT_PHONE_FOR_TESTING, crm, expertise, mockAddressData());
    }

    private DadosPaciente mockPatientData(String name, String email, String cpf) {
        return new DadosPaciente(
                name, email, "61999999999", cpf, mockAddressData());
    }

    private DadosEndereco mockAddressData() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                "DF",
                "00918123");
    }
}
