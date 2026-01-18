package com.medical.api.models;

import com.medical.api.dto.DoctorCreateRequest;
import com.medical.api.utils.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor @Entity @Table(name = "doctors") public class Doctor extends Person {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Specialty specialty;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    public Doctor(DoctorCreateRequest doctor) {
        super(doctor.name().trim(), doctor.email().trim(), doctor.phone().trim(), new Address(doctor.address()));
        this.specialty = doctor.specialty();
        this.licenseNumber = doctor.licenseNumber();
    }
}