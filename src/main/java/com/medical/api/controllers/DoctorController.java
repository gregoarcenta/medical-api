package com.medical.api.controllers;

import com.medical.api.dto.DoctorRequest;
import com.medical.api.models.Doctor;
import com.medical.api.repository.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
class DoctorController {
    private final DoctorRepository doctorRepository;

    DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    @PostMapping
    public Doctor createDoctor(@RequestBody @Valid DoctorRequest doctor) {
        return doctorRepository.save(new Doctor(doctor));
    }
}
