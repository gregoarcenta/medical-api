package com.medical.api.dto;

import com.medical.api.models.Address;

import java.io.Serializable;

/**
 * DTO for {@link com.medical.api.models.Patient}
 */
public record PatientResponse(Long id, String name, String email, String document, String phone, Address address) implements Serializable {}