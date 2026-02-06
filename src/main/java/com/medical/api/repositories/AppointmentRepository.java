package com.medical.api.repositories;

import com.medical.api.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPatientIdAndDateBetween(Long patientId, LocalDateTime start, LocalDateTime end);
}