package com.medical.api.controllers;

import com.medical.api.dto.PatientCreateRequest;
import com.medical.api.dto.PatientResponse;
import com.medical.api.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public PatientResponse createPatient(@RequestBody @Valid PatientCreateRequest patientRequest) {
        return patientService.createPatient(patientRequest);
    }
}
