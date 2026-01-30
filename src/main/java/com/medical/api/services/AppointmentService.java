package com.medical.api.services;

import com.medical.api.dto.AppointmentRequest;
import com.medical.api.dto.AppointmentResponse;
import com.medical.api.models.Appointment;
import com.medical.api.models.Doctor;
import com.medical.api.models.Patient;
import com.medical.api.repositories.AppointmentRepository;
import com.medical.api.repositories.DoctorRepository;
import com.medical.api.repositories.PatientRepository;
import com.medical.api.utils.Specialty;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Doctor doctor = selectDoctor(request.doctorId(), request.specialty(), request.date());

        Patient patient = patientRepository.findByIdAndActiveTrue(request.patientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Appointment appointment = new Appointment(null, doctor, patient, request.date());
        var appointmentSaved = this.appointmentRepository.save(appointment);

        return toAppointmentResponse(appointmentSaved);
    }

    private Doctor selectDoctor(Long doctorId, Specialty specialty, LocalDateTime date) {
        if (doctorId != null) {
            return doctorRepository.findByIdAndActiveTrue(doctorId)
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        }

        if (specialty == null) {
            throw new IllegalArgumentException("Specialty is required when doctorId is null");
        }

        LocalDateTime start = date.minusMinutes(30).truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime end = date.plusMinutes(30).truncatedTo(ChronoUnit.MINUTES);

        System.out.println(start + " - " + end);

        long availableCount = doctorRepository.countAvailableDoctors(specialty, start, end);
        if (availableCount == 0) {
            throw new EntityNotFoundException("No available doctors for the requested specialty and date");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt((int) availableCount);
        return doctorRepository.findAvailableDoctors(specialty, start, end, PageRequest.of(randomIndex, 1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No available doctors for the requested specialty and date"));
    }

    private AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDoctor().getId(),
                appointment.getPatient().getId(),
                appointment.getDate()
        );
    }
}
