package com.medical.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.medical.api.models.User}
 */
public record AuthRequest(@NotBlank(message = "El nombre de usuario es obligatorio") String username,
                          @NotBlank(message = "La contraseña es obligatoria") String password)
        implements Serializable {}