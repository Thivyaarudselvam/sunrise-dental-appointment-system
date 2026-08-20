package com.example.demo.controller;

import com.example.demo.model.Treatment;
import com.example.demo.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @PostMapping
    public Treatment addTreatment(@RequestBody Treatment treatment) {
        return treatmentService.addTreatment(treatment);
    }

    @GetMapping
    public List<Treatment> getAllTreatments() {
        return treatmentService.getAllTreatments();
    }
}
