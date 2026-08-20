package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment registerAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> searchByAppointmentNumber(Long appointmentNumber) {
        return appointmentRepository.findById(appointmentNumber);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
