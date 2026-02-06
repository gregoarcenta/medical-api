package com.medical.api.validations;

import com.medical.api.dto.AppointmentRequest;
import com.medical.api.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class PatientDailyAppointmentValidator implements IValidator {
    private final AppointmentRepository appointmentRepository;

    public PatientDailyAppointmentValidator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void validate(AppointmentRequest appointmentRequest) {
        LocalDate date = appointmentRequest.date().toLocalDate();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        boolean hasAppointmentSameDay = appointmentRepository.existsByPatientIdAndDateBetween(
                appointmentRequest.patientId(),
                start,
                end
        );

        if (hasAppointmentSameDay) {
            throw new IllegalArgumentException("El paciente ya tiene una cita para ese dia.");
        }
    }
}

