package com.medical.api.services;

import com.medical.api.dto.PatientCreateRequest;
import com.medical.api.dto.PatientResponse;
import com.medical.api.dto.UpdateRequest;
import com.medical.api.models.Patient;
import com.medical.api.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientService {
    private final PatientRepository patientRepository;

    PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse createPatient(PatientCreateRequest patientRequest) {
        var patientCreated = patientRepository.save(new Patient(patientRequest));
        return toPatientResponse(patientCreated);
    }

    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        return patientRepository.findByActiveTrue(pageable).map(this::toPatientResponse);
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        return patientRepository.findByIdAndActiveTrue(id)
                .map(this::toPatientResponse)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    public PatientResponse updatePatient(Long id, UpdateRequest patientRequest) {
        if (!patientRepository.existsByIdAndActiveTrue(id)) {
            throw new EntityNotFoundException("Patient with ID " + id + " not found or is inactive");
        }
        Patient patient = patientRepository.getReferenceById(id);
        patient.update(patientRequest);
        return toPatientResponse(patient);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Delete failed: ID " + id + " does not exist");
        }
        Patient patient = patientRepository.getReferenceById(id);
        patient.delete();
    }

    private PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getDocument(),
                patient.getPhone(),
                patient.getAddress()
        );
    }
}
