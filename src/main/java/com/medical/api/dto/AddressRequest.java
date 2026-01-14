package com.medical.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRequest(
        @NotBlank(message = "El país es obligatorio")
        String country,
        
        @NotBlank(message = "El estado es obligatorio")
        String state,
        
        @NotBlank(message = "La ciudad es obligatoria")
        String city,
        
        @NotBlank(message = "La calle es obligatoria")
        String street,
        
        @NotBlank(message = "El código postal es obligatorio")
        @Pattern(regexp = "^\\d{5}$", 
                message = "El código postal debe tener exactamente 5 dígitos")
        String postalCode,

        String number,
        
        String complement
) {
}
