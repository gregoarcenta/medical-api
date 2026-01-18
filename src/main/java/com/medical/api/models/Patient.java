package com.medical.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @Table(name = "patients") public class Patient
        extends Person {

    @Column(nullable = false, unique = true, length = 13)
    private String document;

//    public Patient(String name, String email, String phone, Address address, String document) {
//        super(name, email, phone, address);
//        this.document = document;
//    }
}