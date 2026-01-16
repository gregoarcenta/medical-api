package com.medical.api.services;

import com.medical.api.dto.DoctorCreateRequest;
import com.medical.api.dto.DoctorResponse;
import com.medical.api.dto.DoctorUpdateRequest;
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

    public DoctorResponse createDoctor(DoctorCreateRequest doctor) {
        Doctor doctorCreated = doctorRepository.save(new Doctor(doctor));
        return toResponse(doctorCreated);
    }

    @Transactional(readOnly = true)
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(this::toResponse);
    }

    public DoctorResponse updateDoctor(Long id, DoctorUpdateRequest doctor) {
//        if (!doctorRepository.existsById(id)) {
//            throw new DoctorNotFoundException("Doctor con ID " + id + " no encontrado");
//        }
        Doctor doctorRef = doctorRepository.getReferenceById(id);
        doctorRef.update(doctor);
        return toResponse(doctorRepository.save(doctorRef));
    }

    private DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getSpecialty(),
                doctor.getLicenseNumber()
        );
    }
}
