package com.example.demo.service;

import com.example.demo.model.Treatment;
import com.example.demo.repository.TreatmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TreatmentServiceTest {

    @Mock
    private TreatmentRepository treatmentRepository;

    @InjectMocks
    private TreatmentService treatmentService;

    private Treatment sampleTreatment;

    @BeforeEach
    void setUp() {
        sampleTreatment = new Treatment("Tooth Extraction", new BigDecimal("3500.00"));
        sampleTreatment.setTreatmentId(1L);
    }

    @Test
    @DisplayName("Adding a valid treatment should save and return the treatment")
    void testAddTreatment_Success() {
        when(treatmentRepository.save(any(Treatment.class))).thenReturn(sampleTreatment);

        Treatment result = treatmentService.addTreatment(sampleTreatment);

        assertNotNull(result);
        assertEquals("Tooth Extraction", result.getTreatmentName());
        assertEquals(0, new BigDecimal("3500.00").compareTo(result.getCost()));
        verify(treatmentRepository, times(1)).save(sampleTreatment);
    }

    @Test
    @DisplayName("Getting all treatments should return the list from the repository")
    void testGetAllTreatments() {
        when(treatmentRepository.findAll()).thenReturn(Collections.singletonList(sampleTreatment));

        List<Treatment> result = treatmentService.getAllTreatments();

        assertEquals(1, result.size());
        verify(treatmentRepository, times(1)).findAll();
    }
}
