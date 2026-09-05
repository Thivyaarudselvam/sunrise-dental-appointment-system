package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Dentist;
import com.example.demo.model.Patient;
import com.example.demo.model.Treatment;
import com.example.demo.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment sampleAppointment;

    @BeforeEach
    void setUp() {
        Patient patient = new Patient("Nimal Perera", "45 Galle Road", "0771234567");
        Dentist dentist = new Dentist("Dr. Kamal Silva", "Orthodontics");
        Treatment treatment = new Treatment("Tooth Extraction", new BigDecimal("3500.00"));

        sampleAppointment = new Appointment(patient, dentist, treatment,
                LocalDate.of(2026, 9, 5), LocalTime.of(10, 30));
        sampleAppointment.setAppointmentNumber(1L);
    }

    @Test
    @DisplayName("Registering a valid appointment should save and return it with an appointment number")
    void testRegisterAppointment_Success() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(sampleAppointment);

        Appointment result = appointmentService.registerAppointment(sampleAppointment);

        assertNotNull(result);
        assertEquals(1L, result.getAppointmentNumber());
        assertEquals("Nimal Perera", result.getPatient().getName());
        verify(appointmentRepository, times(1)).save(sampleAppointment);
    }

    @Test
    @DisplayName("Searching by a valid appointment number should return the appointment")
    void testSearchByAppointmentNumber_Found() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(sampleAppointment));

        Optional<Appointment> result = appointmentService.searchByAppointmentNumber(1L);

        assertTrue(result.isPresent());
        assertEquals("Dr. Kamal Silva", result.get().getDentist().getName());
    }

    @Test
    @DisplayName("Searching by an invalid appointment number should return empty")
    void testSearchByAppointmentNumber_NotFound() {
        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Appointment> result = appointmentService.searchByAppointmentNumber(999L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Getting all appointments should return the full list")
    void testGetAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(Collections.singletonList(sampleAppointment));

        List<Appointment> result = appointmentService.getAllAppointments();

        assertEquals(1, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Registering an appointment with a missing required field should be rejected")
    void testRegisterAppointment_MissingDate_ThrowsException() {
        sampleAppointment.setAppointmentDate(null);

        assertThrows(IllegalArgumentException.class,
                () -> appointmentService.registerAppointment(sampleAppointment));

        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}
