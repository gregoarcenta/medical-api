package com.medical.api.dto;

import com.medical.api.models.Address;
import com.medical.api.utils.Specialty;

import java.io.Serializable;

/**
 * DTO for {@link com.medical.api.models.Doctor}
 */
public record DoctorResponse(Long id, String name, String email, String phone, Specialty specialty, String licenseNumber, Address address)
        implements Serializable {
}