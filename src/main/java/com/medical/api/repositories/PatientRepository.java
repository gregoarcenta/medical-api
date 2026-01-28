package com.medical.api.repositories;

import com.medical.api.models.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByIdAndActiveTrue(Long id);

    Page<Patient> findByActiveTrue(Pageable pageable);

    boolean existsByIdAndActiveTrue(Long id);
}