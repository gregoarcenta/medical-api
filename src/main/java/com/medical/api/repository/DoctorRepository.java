package com.medical.api.repository;

import com.medical.api.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByIdAndActiveTrue(Long id);

    Page<Doctor> findByActiveTrue(Pageable pageable);
}