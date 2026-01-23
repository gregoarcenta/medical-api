package com.medical.api.infra.errors;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        String title,
        int status,
        LocalDateTime timestamp,
        String detail,
        String path,
        List<ValidationError> errors // Solo para 400 Bad Request
) {
    public record ValidationError(String field, String message) {}
}