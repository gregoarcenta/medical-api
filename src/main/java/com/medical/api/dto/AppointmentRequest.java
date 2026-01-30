package com.medical.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medical.api.utils.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(
        Long doctorId,
        @NotNull Long patientId,
        Specialty specialty,
        @NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date
) {
}
