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
        if (appointment.getPatient() == null) {
            throw new IllegalArgumentException("A patient must be selected for the appointment.");
        }
        if (appointment.getDentist() == null) {
            throw new IllegalArgumentException("A dentist must be selected for the appointment.");
        }
        if (appointment.getTreatment() == null) {
            throw new IllegalArgumentException("A treatment must be selected for the appointment.");
        }
        if (appointment.getAppointmentDate() == null) {
            throw new IllegalArgumentException("Appointment date is required.");
        }
        if (appointment.getAppointmentTime() == null) {
            throw new IllegalArgumentException("Appointment time is required.");
        }
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> searchByAppointmentNumber(Long appointmentNumber) {
        return appointmentRepository.findById(appointmentNumber);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
