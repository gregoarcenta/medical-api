package com.medical.api.models;

import com.medical.api.dto.DoctorUpdateRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    private Boolean active = true;

    @Embedded
    private Address address;

    public Person(String name, String email, String phone, Address address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public void update(@Valid DoctorUpdateRequest doctorRequest) {
        if (doctorRequest.name() != null && !doctorRequest.name().isBlank()) {
            this.setName(doctorRequest.name().trim());
        }
        if (doctorRequest.phone() != null && !doctorRequest.phone().isBlank()) {
            this.setPhone(doctorRequest.phone().trim());
        }
        if (doctorRequest.address() != null) {
            this.getAddress().update(doctorRequest.address());
        }
    }

    public void delete() {
        this.active = false;
    }
}