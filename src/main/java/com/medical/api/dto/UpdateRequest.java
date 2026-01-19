package com.medical.api.dto;

import com.medical.api.models.Doctor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Doctor}
 */
public record UpdateRequest(
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String name,
        @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener exactamente 9 dígitos") String phone,
        @Valid AddressRequest address) implements Serializable {
}