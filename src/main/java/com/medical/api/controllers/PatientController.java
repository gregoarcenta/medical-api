package com.medical.api.controllers;

import com.medical.api.dto.PatientCreateRequest;
import com.medical.api.dto.PatientResponse;
import com.medical.api.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Page<PatientResponse> getAllPatients(
            @PageableDefault(sort = {"name"}) Pageable pageable
    ) {
        return patientService.getAllPatients(pageable);
    }
}
