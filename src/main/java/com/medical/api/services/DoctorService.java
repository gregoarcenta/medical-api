package com.medical.api.services;

import com.medical.api.dto.DoctorRequest;
import com.medical.api.dto.DoctorResponse;
import com.medical.api.models.Doctor;
import com.medical.api.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(DoctorRequest doctor) {
        return doctorRepository.save(new Doctor(doctor));
    }

    @Transactional(readOnly = true)
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(this::toResponse);
    }

    private DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(doctor.getName(),
                                  doctor.getEmail(),
                                  doctor.getSpecialty(),
                                  doctor.getLicenseNumber()
        );
    }
}
