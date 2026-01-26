package com.medical.api.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medical.api.infra.errors.ApiErrorResponse;
import com.medical.api.infra.security.exceptions.TokenExpiredException;
import com.medical.api.infra.security.exceptions.TokenInvalidException;
import com.medical.api.infra.security.exceptions.UserDisabledException;
import com.medical.api.infra.security.exceptions.UserNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String title;
        String detail;
        int status;

        switch (authException) {
            case TokenExpiredException e -> {
                title = "Token Expired";
                detail = e.getMessage();
                status = HttpStatus.UNAUTHORIZED.value();
            }
            case TokenInvalidException e -> {
                title = "Token Invalid";
                detail = e.getMessage();
                status = HttpStatus.UNAUTHORIZED.value();
            }
            case UserNotFoundException e -> {
                title = "User Not Found";
                detail = e.getMessage();
                status = HttpStatus.UNAUTHORIZED.value();
            }
            case UserDisabledException e -> {
                title = "Account Disabled";
                detail = e.getMessage();
                status = HttpStatus.FORBIDDEN.value();
            }
            default -> {
                title = "Authentication Required";
                detail = "You must provide a valid JWT token in the Authorization header.";
                status = HttpStatus.UNAUTHORIZED.value();
            }
        }

        response.setStatus(status);

        var errorResponse = new ApiErrorResponse(
                title,
                status,
                LocalDateTime.now(),
                detail,
                request.getRequestURI(),
                null
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}