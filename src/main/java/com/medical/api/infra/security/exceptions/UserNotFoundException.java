package com.medical.api.infra.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super("User associated with this token no longer exists.");
    }
}
