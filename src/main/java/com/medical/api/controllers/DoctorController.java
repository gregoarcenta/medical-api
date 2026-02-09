package com.medical.api.controllers;

import com.medical.api.dto.DoctorCreateRequest;
import com.medical.api.dto.DoctorResponse;
import com.medical.api.dto.UpdateRequest;
import com.medical.api.services.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController @RequestMapping("/doctors") @SecurityRequirement(name = "bearer-key")
@Tag(name = "Médicos", description = "Endpoints para la gestión de doctores y sus especialidades")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo médico", description = "Crea un médico en la base de datos y retorna su información.")
    public ResponseEntity<DoctorResponse> createDoctor(
            @RequestBody @Valid DoctorCreateRequest doctorRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var doctor = doctorService.createDoctor(doctorRequest);
        var uri = uriComponentsBuilder.path("/doctors/{id}").build(doctor.id());
        return ResponseEntity.created(uri).body(doctor);
    }

    @GetMapping
    @Operation(summary = "Listado de médicos", description = "Obtiene una lista paginada de los médicos activos.")
    public ResponseEntity<Page<DoctorResponse>> getAllDoctors(
            @ParameterObject @PageableDefault(sort = {"name"}) Pageable pageable
    ) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un médico", description = "Retorna los datos de un médico específico por su ID.")
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar médico", description = "Permite modificar datos parciales de un médico.")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long id,
            @RequestBody UpdateRequest doctorRequest
    ) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar médico", description = "Realiza un borrado lógico del médico en el sistema.")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
