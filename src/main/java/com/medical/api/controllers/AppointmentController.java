package com.medical.api.controllers;

import com.medical.api.dto.AppointmentRequest;
import com.medical.api.dto.AppointmentResponse;
import com.medical.api.infra.errors.ApiErrorResponse;
import com.medical.api.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/appointments") @SecurityRequirement(name = "bearer-key")
@Tag(name = "Consultas", description = "Operaciones para agendar y gestionar las citas médicas entre pacientes y doctores")
class AppointmentController {

    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(summary = "Agendar una nueva consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta agendada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Médico o Paciente no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        var appointmentResponse = appointmentService.createAppointment(request);
        return ResponseEntity.ok(appointmentResponse);
    }
}