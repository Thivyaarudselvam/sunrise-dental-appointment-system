package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.model.Bill;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/generate/{appointmentNumber}")
    public ResponseEntity<?> generateBill(@PathVariable Long appointmentNumber) {
        return appointmentService.searchByAppointmentNumber(appointmentNumber)
                .<ResponseEntity<?>>map(appointment -> {
                    Bill bill = billService.generateBill(appointment);
                    return ResponseEntity.ok(bill);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
