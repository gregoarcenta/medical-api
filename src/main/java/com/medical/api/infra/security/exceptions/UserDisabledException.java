package com.medical.api.infra.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserDisabledException extends AuthenticationException {
    public UserDisabledException() {
        super("User account is disabled. Please contact support.");
    }
}
