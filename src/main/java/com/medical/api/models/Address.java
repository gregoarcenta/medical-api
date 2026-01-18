package com.medical.api.models;

import com.medical.api.dto.AddressRequest;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    private String country;
    private String state;
    private String city;
    private String street;
    private String postalCode;
    private String number;
    private String complement;

    public Address(AddressRequest address) {
        this.country = address.country();
        this.state = address.state();
        this.city = address.city();
        this.street = address.street();
        this.postalCode = address.postalCode();
        this.number = address.number();
        this.complement = address.complement();
    }

    public void update(AddressRequest addressRequest) {
        if (addressRequest.country() != null) {
            this.country = addressRequest.country().trim();
        }
        if (addressRequest.state() != null) {
            this.state = addressRequest.state().trim();
        }
        if (addressRequest.city() != null) {
            this.city = addressRequest.city().trim();
        }
        if (addressRequest.street() != null) {
            this.street = addressRequest.street().trim();
        }
        if (addressRequest.postalCode() != null) {
            this.postalCode = addressRequest.postalCode().trim();
        }
        if (addressRequest.number() != null) {
            this.number = addressRequest.number().trim();
        }
        if (addressRequest.complement() != null) {
            this.complement = addressRequest.complement().trim();
        }
    }
}