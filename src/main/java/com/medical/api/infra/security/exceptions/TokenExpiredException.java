package com.medical.api.infra.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException() {
        super("Token JWT has expired. Please log in again.");
    }
}
