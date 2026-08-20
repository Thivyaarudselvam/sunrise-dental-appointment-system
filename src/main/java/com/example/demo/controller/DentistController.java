package com.example.demo.controller;

import com.example.demo.model.Dentist;
import com.example.demo.service.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dentists")
public class DentistController {

    @Autowired
    private DentistService dentistService;

    @PostMapping
    public Dentist addDentist(@RequestBody Dentist dentist) {
        return dentistService.addDentist(dentist);
    }

    @GetMapping
    public List<Dentist> getAllDentists() {
        return dentistService.getAllDentists();
    }
}
