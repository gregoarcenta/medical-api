package com.medical.api.services;

import com.medical.api.dto.AppointmentRequest;
import com.medical.api.dto.AppointmentResponse;
import com.medical.api.models.Appointment;
import com.medical.api.models.Doctor;
import com.medical.api.models.Patient;
import com.medical.api.repositories.AppointmentRepository;
import com.medical.api.repositories.DoctorRepository;
import com.medical.api.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Doctor doctor = doctorRepository.findByIdAndActiveTrue(request.doctorId()).orElseThrow();
        Patient patient = patientRepository.findByIdAndActiveTrue(request.patientId()).orElseThrow();

        Appointment appointment = new Appointment(null, doctor, patient, request.date());
        var appointmentSaved = this.appointmentRepository.save(appointment);

        return toAppointmentResponse(appointmentSaved);
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
