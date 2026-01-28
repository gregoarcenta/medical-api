package com.medical.api.controllers;

import com.medical.api.dto.AppointmentRequest;
import com.medical.api.dto.AppointmentResponse;
import com.medical.api.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
class AppointmentController {

    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        var appointmentResponse = appointmentService.createAppointment(request);
        return ResponseEntity.ok(appointmentResponse);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable Long id) { }
}
