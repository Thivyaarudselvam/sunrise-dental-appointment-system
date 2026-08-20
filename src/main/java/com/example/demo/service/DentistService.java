package com.example.demo.service;

import com.example.demo.model.Dentist;
import com.example.demo.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistService {

    @Autowired
    private DentistRepository dentistRepository;

    public Dentist addDentist(Dentist dentist) {
        return dentistRepository.save(dentist);
    }

    public List<Dentist> getAllDentists() {
        return dentistRepository.findAll();
    }
}
