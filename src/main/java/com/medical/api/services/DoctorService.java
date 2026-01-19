package com.medical.api.services;

import com.medical.api.dto.DoctorCreateRequest;
import com.medical.api.dto.DoctorResponse;
import com.medical.api.dto.UpdateRequest;
import com.medical.api.models.Doctor;
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
        return doctorRepository.getByActiveTrue(pageable).map(this::toResponse);
    }

    public DoctorResponse updateDoctor(Long id, UpdateRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor == null) return null;

        doctor.update(doctorRequest);

        return toResponse(doctor);
    }

    public String deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor == null) return null;

        doctor.delete();

        return "El medico ha sido eliminado";
    }

    private DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(doctor.getId(),
                                  doctor.getName(),
                                  doctor.getEmail(),
                                  doctor.getSpecialty(),
                                  doctor.getLicenseNumber()
        );
    }

}
