package com.medical.api.controllers;

import com.medical.api.dto.DoctorRequest;
import com.medical.api.dto.DoctorResponse;
import com.medical.api.models.Doctor;
import com.medical.api.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody @Valid DoctorRequest doctor) {
        return doctorService.createDoctor(doctor);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponse>> getAllDoctors(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }
}
