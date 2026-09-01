package com.example.demo.service;

import com.example.demo.model.Dentist;
import com.example.demo.repository.DentistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DentistServiceTest {

    @Mock
    private DentistRepository dentistRepository;

    @InjectMocks
    private DentistService dentistService;

    private Dentist sampleDentist;

    @BeforeEach
    void setUp() {
        sampleDentist = new Dentist("Dr. Kamal Silva", "Orthodontics");
        sampleDentist.setDentistId(1L);
    }

    @Test
    @DisplayName("Adding a valid dentist should save and return the dentist")
    void testAddDentist_Success() {
        when(dentistRepository.save(any(Dentist.class))).thenReturn(sampleDentist);

        Dentist result = dentistService.addDentist(sampleDentist);

        assertNotNull(result);
        assertEquals("Dr. Kamal Silva", result.getName());
        assertEquals("Orthodontics", result.getSpecialization());
        verify(dentistRepository, times(1)).save(sampleDentist);
    }

    @Test
    @DisplayName("Getting all dentists should return the list from the repository")
    void testGetAllDentists() {
        when(dentistRepository.findAll()).thenReturn(Collections.singletonList(sampleDentist));

        List<Dentist> result = dentistService.getAllDentists();

        assertEquals(1, result.size());
        assertEquals("Dr. Kamal Silva", result.get(0).getName());
        verify(dentistRepository, times(1)).findAll();
    }
}
