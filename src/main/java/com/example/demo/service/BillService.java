package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Bill;
import com.example.demo.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    private static final BigDecimal CONSULTATION_FEE = new BigDecimal("500.00");

    public Bill generateBill(Appointment appointment) {
        BigDecimal treatmentCost = appointment.getTreatment().getCost();
        Bill bill = new Bill(appointment, CONSULTATION_FEE, treatmentCost);
        return billRepository.save(bill);
    }
}
