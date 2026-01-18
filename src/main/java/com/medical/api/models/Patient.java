package com.medical.api.models;

import com.medical.api.dto.PatientCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients") public class Patient extends Person {

    @Column(nullable = false, unique = true, length = 13)
    private String document;

    public Patient(PatientCreateRequest patient) {
        super(patient.name(), patient.email(), patient.phone(), new Address(patient.address()));
        this.document = patient.document();
    }
}