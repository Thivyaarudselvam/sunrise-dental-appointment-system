package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the bill generation business rule: total = consultation fee + treatment cost.
 * This is the core calculation logic required by the assessment brief
 * ("Calculate the total treatment cost based on treatment type and consultation fee").
 */
@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillService billService;

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
    @DisplayName("Generated bill total should equal consultation fee plus treatment cost")
    void testGenerateBill_CorrectTotal() {
        ArgumentCaptor<Bill> billCaptor = ArgumentCaptor.forClass(Bill.class);
        when(billRepository.save(billCaptor.capture())).thenAnswer(inv -> inv.getArgument(0));

        Bill result = billService.generateBill(sampleAppointment);

        // Consultation fee (500.00) + treatment cost (3500.00) = 4000.00
        assertEquals(0, new BigDecimal("4000.00").compareTo(result.getTotalAmount()));
        assertEquals(0, new BigDecimal("500.00").compareTo(result.getConsultationFee()));
        assertEquals(0, new BigDecimal("3500.00").compareTo(result.getTreatmentCost()));
        verify(billRepository, times(1)).save(any(Bill.class));
    }

    @Test
    @DisplayName("Generated bill should be linked to the correct appointment")
    void testGenerateBill_LinksAppointment() {
        when(billRepository.save(any(Bill.class))).thenAnswer(inv -> inv.getArgument(0));

        Bill result = billService.generateBill(sampleAppointment);

        assertEquals(1L, result.getAppointment().getAppointmentNumber());
        assertEquals("Nimal Perera", result.getAppointment().getPatient().getName());
    }
}
