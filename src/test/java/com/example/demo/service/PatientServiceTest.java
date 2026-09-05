package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PatientService.
 * Uses Mockito to mock the repository layer so the service logic
 * can be tested in isolation (Test-Driven Development approach).
 */
@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient samplePatient;

    @BeforeEach
    void setUp() {
        samplePatient = new Patient("Nimal Perera", "45 Galle Road, Colombo", "0771234567");
        samplePatient.setPatientId(1L);
    }

    @Test
    @DisplayName("Registering a valid patient should save and return the patient")
    void testRegisterPatient_Success() {
        when(patientRepository.save(any(Patient.class))).thenReturn(samplePatient);

        Patient result = patientService.registerPatient(samplePatient);

        assertNotNull(result);
        assertEquals("Nimal Perera", result.getName());
        assertEquals("0771234567", result.getContactNumber());
        verify(patientRepository, times(1)).save(samplePatient);
    }

    @Test
    @DisplayName("Getting all patients should return the full list from the repository")
    void testGetAllPatients() {
        Patient p2 = new Patient("Kamala Fernando", "12 Kandy Road", "0779876543");
        when(patientRepository.findAll()).thenReturn(Arrays.asList(samplePatient, p2));

        List<Patient> result = patientService.getAllPatients();

        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Getting a patient by a valid ID should return that patient")
    void testGetPatientById_Found() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(samplePatient));

        Optional<Patient> result = patientService.getPatientById(1L);

        assertTrue(result.isPresent());
        assertEquals("Nimal Perera", result.get().getName());
    }

    @Test
    @DisplayName("Getting a patient by an invalid ID should return empty")
    void testGetPatientById_NotFound() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Patient> result = patientService.getPatientById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Registering a patient with a missing required field should be rejected")
    void testRegisterPatient_MissingName_ThrowsException() {
        Patient invalidPatient = new Patient("", "No 12", "0771234567");

        assertThrows(IllegalArgumentException.class,
                () -> patientService.registerPatient(invalidPatient));

        verify(patientRepository, never()).save(any(Patient.class));
    }
}
