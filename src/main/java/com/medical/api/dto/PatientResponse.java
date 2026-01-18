package com.medical.api.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.medical.api.models.Doctor}
 */
public record PatientResponse(Long id, String name, String email, String document) implements Serializable {}