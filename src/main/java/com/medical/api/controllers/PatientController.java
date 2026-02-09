package com.medical.api.controllers;

import com.medical.api.dto.PatientCreateRequest;
import com.medical.api.dto.PatientResponse;
import com.medical.api.dto.UpdateRequest;
import com.medical.api.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Pacientes", description = "Operaciones para la gestión de pacientes y sus historias clínicas")
class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Registrar un paciente", description = "Añade un nuevo paciente al sistema. Requiere datos de contacto y dirección.")
    @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public ResponseEntity<PatientResponse> createPatient(@RequestBody @Valid PatientCreateRequest patientRequest, UriComponentsBuilder uriComponentsBuilder) {
        var patient = patientService.createPatient(patientRequest);
        var uri = uriComponentsBuilder.path("patients/{id}").build(patient.id());
        return ResponseEntity.created(uri).body(patient);
    }

    @GetMapping
    @Operation(summary = "Listado paginado de pacientes")
    public ResponseEntity<Page<PatientResponse>> getAllPatients(
            @ParameterObject @PageableDefault(sort = {"name"}) Pageable pageable // <--- Limpia la interfaz de Swagger
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paciente por ID")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    @ApiResponse(responseCode = "404", description = "El paciente no existe en la base de datos")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar datos del paciente")
    public ResponseEntity<PatientResponse> updateDoctor(@PathVariable Long id, @RequestBody UpdateRequest patientRequest) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Marca al paciente como inactivo (borrado lógico).")
    @ApiResponse(responseCode = "204", description = "Paciente eliminado correctamente")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
