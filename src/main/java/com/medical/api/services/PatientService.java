package com.medical.api.services;

import com.medical.api.dto.PatientCreateRequest;
import com.medical.api.dto.PatientResponse;
import com.medical.api.dto.UpdateRequest;
import com.medical.api.models.Patient;
import com.medical.api.models.Person;
import com.medical.api.repository.PatientRepository;
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
        return toPatientResponse(patientRepository.findByIdAndActiveTrue(id).orElseThrow());
    }

    public PatientResponse updatePatient(Long id, UpdateRequest patientRequest) {
        Patient patient = patientRepository.findById(id).orElse(null);

        if (patient == null) return null;

        patient.update(patientRequest);

        return toPatientResponse(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.findById(id).ifPresent(Person::delete);
    }

    private PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getDocument()
        );
    }
}
