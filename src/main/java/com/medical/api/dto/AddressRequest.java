package com.medical.api.dto;

public record AddressRequest(String country, String state, String city, String street, String postalCode,
                             String number, String complement) {
}
