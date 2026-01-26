package com.medical.api.infra.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenInvalidException extends AuthenticationException {
    public TokenInvalidException() {
        super("Token JWT is invalid or malformed.");
    }
}
