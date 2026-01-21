package com.medical.api.services;

import com.medical.api.dto.DoctorCreateRequest;
import com.medical.api.dto.DoctorResponse;
import com.medical.api.dto.UpdateRequest;
import com.medical.api.models.Doctor;
import com.medical.api.models.Person;
import com.medical.api.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponse createDoctor(DoctorCreateRequest doctor) {
        Doctor doctorCreated = doctorRepository.save(new Doctor(doctor));
        return toResponse(doctorCreated);
    }

    @Transactional(readOnly = true)
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {
        return doctorRepository.findByActiveTrue(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id) {
        return toResponse(doctorRepository.findByIdAndActiveTrue(id).orElseThrow());
    }

    public DoctorResponse updateDoctor(Long id, UpdateRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor == null) return null;

        doctor.update(doctorRequest);

        return toResponse(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.findById(id).ifPresent(Person::delete);
    }

    private DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(doctor.getId(),
                                  doctor.getName(),
                                  doctor.getEmail(),
                                  doctor.getPhone(),
                                  doctor.getSpecialty(),
                                  doctor.getLicenseNumber(),
                                  doctor.getAddress()
        );
    }

}
