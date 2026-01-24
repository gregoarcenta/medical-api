package com.medical.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice public class GlobalExceptionHandler {

    // 404 - Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage(), request);
    }

    // 401 - Bad Credentials (Login fallido)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid Credentials", "User or password incorrect", request);
    }

    // 401 - Unauthorized (Cualquier otro error de auth)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationError(
            AuthenticationException ex,
            HttpServletRequest request
    ) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication Error", ex.getMessage(), request);
    }

    // 400 - Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ApiErrorResponse.ValidationError> validationErrors = ex.getFieldErrors()
                .stream()
                .map(f -> new ApiErrorResponse.ValidationError(f.getField(), f.getDefaultMessage()))
                .toList();

        var error = new ApiErrorResponse("Validation Failed",
                                         HttpStatus.BAD_REQUEST.value(),
                                         LocalDateTime.now(),
                                         "The request content was invalid",
                                         request.getRequestURI(),
                                         validationErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 400 - Malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        return buildResponse(HttpStatus.BAD_REQUEST,
                             "Malformed JSON Request",
                             "Check your JSON structure or Enum values",
                             request
        );
    }

    // 409 - Data Conflict
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        return buildResponse(HttpStatus.CONFLICT,
                             "Data Integrity Violation",
                             "Duplicate entry or constraint violation",
                             request
        );
    }

    // 500 - Catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalError(Exception ex, HttpServletRequest request) {
        // En producción, usa un Logger aquí: log.error("Internal error", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                             "Internal Server Error",
                             "An unexpected error occurred",
                             request
        );
    }

    // Método privado para evitar repetir código (DRY)
    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request
    ) {
        var error = new ApiErrorResponse(title,
                                         status.value(),
                                         LocalDateTime.now(),
                                         detail,
                                         request.getRequestURI(),
                                         null
        );
        return ResponseEntity.status(status).body(error);
    }
}