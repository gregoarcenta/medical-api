package com.medical.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull Long doctorId, @NotNull Long patientId,
        @NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date
) {
}
