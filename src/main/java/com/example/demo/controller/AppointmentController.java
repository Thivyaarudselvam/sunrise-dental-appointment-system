package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public Appointment registerAppointment(@RequestBody Appointment appointment) {
        return appointmentService.registerAppointment(appointment);
    }

    @GetMapping("/{appointmentNumber}")
    public ResponseEntity<Appointment> searchAppointment(@PathVariable Long appointmentNumber) {
        return appointmentService.searchByAppointmentNumber(appointmentNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}
