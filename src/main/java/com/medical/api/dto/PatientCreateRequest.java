package com.medical.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record PatientCreateRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^\\d{9}$",
                message = "El teléfono debe tener exactamente 9 dígitos")
        String phone,

        @NotBlank(message = "El documento es obligatorio")
        @Pattern(regexp = "^\\d{10}$|^\\d{13}$", message = "El documento debe tener 10 o 13 dígitos")
        String document,

        @Valid
        @NotNull(message = "La dirección es obligatoria")
        AddressRequest address
) {
}
