package com.medical.api.models;

import com.medical.api.dto.DoctorCreateRequest;
import com.medical.api.dto.DoctorUpdateRequest;
import com.medical.api.utils.Specialty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Specialty specialty;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    private Boolean active = true;

    @Embedded
    private Address address;

    public Doctor(DoctorCreateRequest doctor) {
        this.name = doctor.name().trim();
        this.email = doctor.email().trim();
        this.phone = doctor.phone().trim();
        this.specialty = doctor.specialty();
        this.licenseNumber = doctor.licenseNumber();
        this.address = new Address(doctor.address());
    }

    public void update(@Valid DoctorUpdateRequest doctorRequest) {
        if (doctorRequest.name() != null && !doctorRequest.name().isBlank()) {
            this.name = doctorRequest.name().trim();
        }
        if (doctorRequest.phone() != null && !doctorRequest.phone().isBlank()) {
            this.phone = doctorRequest.phone().trim();
        }
        if (doctorRequest.address() != null) {
            this.address.update(doctorRequest.address());
        }
    }
}