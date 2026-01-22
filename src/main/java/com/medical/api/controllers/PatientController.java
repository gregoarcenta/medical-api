package com.medical.api.controllers;

import com.medical.api.dto.PatientCreateRequest;
import com.medical.api.dto.PatientResponse;
import com.medical.api.dto.UpdateRequest;
import com.medical.api.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patients")
class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody @Valid PatientCreateRequest patientRequest, UriComponentsBuilder uriComponentsBuilder) {
        var patient = patientService.createPatient(patientRequest);
        var uri = uriComponentsBuilder.path("patients/{id}").build(patient.id());
        return ResponseEntity.created(uri).body(patient);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getAllPatients(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponse> updateDoctor(@PathVariable Long id, @RequestBody UpdateRequest patientRequest) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
