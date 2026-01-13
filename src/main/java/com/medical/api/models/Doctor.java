package com.medical.api.models;

import com.medical.api.dto.DoctorRequest;
import com.medical.api.utils.Specialty;
import jakarta.persistence.*;
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

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    private String phone;

    private String licenseNumber;

    @Embedded
    private Address address;

    public Doctor(DoctorRequest doctor) {
        this.name = doctor.name();
        this.email = doctor.email();
        this.phone = doctor.phone();
        this.specialty = doctor.specialty();
        this.licenseNumber = doctor.licenseNumber();
        this.address = new Address(doctor.address());
    }
}