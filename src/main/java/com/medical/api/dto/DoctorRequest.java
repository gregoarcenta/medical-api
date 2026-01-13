package com.medical.api.dto;

import com.medical.api.utils.Specialty;

public record DoctorRequest(
        String name,
        String email,
        Specialty specialty,
        String phone,
        String licenseNumber,
        AddressRequest address
) {
}
