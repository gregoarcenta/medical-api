package com.medical.api.dto;

import java.io.Serializable;

public record AuthResponse(AuthResponseUser user, String token) implements Serializable {

    public record AuthResponseUser(Long id, String user) implements Serializable {
    }
}