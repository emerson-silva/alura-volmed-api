package com.voll.med.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.voll.med.api.domain.appointment.Appointment;
import com.voll.med.api.domain.appointment.AppointmentCalendar;
import com.voll.med.api.domain.appointment.AppointmentDataDTO;
import com.voll.med.api.domain.appointment.AppointmentDetailsDTO;
import com.voll.med.api.domain.medico.Especialidade;
import com.voll.med.api.domain.paciente.DadosPaciente;
import com.voll.med.api.domain.paciente.Paciente;

import lombok.AccessLevel;
import lombok.Getter;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
public class AppointmentControllerTest {
    
    @Getter(value = AccessLevel.PRIVATE)
    @Autowired
    private MockMvc mock;

    @Autowired
    @Getter(value = AccessLevel.PRIVATE)
    private JacksonTester<AppointmentDataDTO> appointmentDataJson;

    @Autowired
    @Getter(value = AccessLevel.PRIVATE)
    private JacksonTester<AppointmentDetailsDTO> appointmentDetailsJson;

    @Getter
    @MockBean
    private AppointmentCalendar calendar;

    @Test
    @DisplayName("Deve retornar erro 400 quando não é passado o body")
    void noBodyScheduleAppointmentRequest () throws Exception {
        var response = getMock().perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar 200 quando passar dados corretor no body do request")
    void scheduleAppointmentWithSuccess () throws Exception {
        var expertise = Especialidade.DERMATOLOGIA;
        var dateTime = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).atTime(8, 0);
        var patientId = 2L;
        var doctorId = 5L;
        var appointmentId = 16L;

        var datailedAppointment = new AppointmentDetailsDTO(appointmentId, patientId, doctorId, dateTime);

        when(getCalendar().scheduleAppointment(any())).thenReturn(datailedAppointment);

        var response = getMock().perform(
            MockMvcRequestBuilders.post("/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(appointmentDataJson.write(
                new AppointmentDataDTO(patientId, doctorId, expertise, dateTime)
            ).getJson())
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = appointmentDetailsJson.write(datailedAppointment).getJson();
        Assertions.assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}
