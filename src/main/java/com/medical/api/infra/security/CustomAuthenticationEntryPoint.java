package com.medical.api.infra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        // Aquí escribimos el JSON manualmente porque estamos fuera del Controller
        String jsonError = """
                {
                    "title": "Authentication Required",
                    "status": 401,
                    "detail": "Token JWT missing or invalid. Please log in.",
                    "path": "%s",
                    "timestamp": "%s"
                }
                """.formatted(request.getRequestURI(), LocalDateTime.now());

        response.getWriter().write(jsonError);
    }
}