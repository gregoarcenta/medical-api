package com.medical.api.dto;

import com.medical.api.utils.Specialty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorRequest(
        @NotBlank(message = "El nombre es obligatorio") 
        String name,
        
        @NotBlank(message = "El email es obligatorio") 
        @Email(message = "Email inválido") 
        String email,
    
        @NotBlank(message = "El número de licencia es obligatorio")
        @Pattern(regexp = "^[A-Z]{2}-\\d{4}-\\d{4}$",
                message = "Formato inválido. Use: XX-1234-1234") 
        String licenseNumber,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^\\d{9}$", 
                message = "El teléfono debe tener exactamente 9 dígitos")
        String phone,
        
        @NotNull(message = "La especialidad es obligatoria") 
        Specialty specialty,
        
        @NotNull(message = "La dirección es obligatoria") 
        @Valid 
        AddressRequest address
) {
}
