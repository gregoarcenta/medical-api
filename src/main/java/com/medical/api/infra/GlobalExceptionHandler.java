package com.medical.api.infra;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        var error = new ApiErrorResponse(
                "Resource Not Found",
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage() == null ? "Resource not found" : ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 400 - Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ApiErrorResponse.ValidationError> validationErrors = ex.getFieldErrors().stream()
                .map(f -> new ApiErrorResponse.ValidationError(f.getField(), f.getDefaultMessage()))
                .toList();

        var error = new ApiErrorResponse(
                "Validation Failed",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "The request content was invalid",
                request.getRequestURI(),
                validationErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 400 - Bad Request (Malformed JSON / Enum Deserialization Error)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        String detail = "Invalid JSON format or field value";

        // Podemos ser más específicos si el error es de un Enum
        if (ex.getMessage() != null && ex.getMessage().contains("Cannot deserialize value of type")) {
            detail = "Invalid value provided for a field (check Enums or Data types).";
        }

        var error = new ApiErrorResponse(
                "Malformed JSON Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                detail,
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 409 - Conflict (Duplicate/Unique Constraint Violation)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        var error = new ApiErrorResponse(
                "Duplicate Entry",
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                "The data you are trying to save already exists",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 500 - Internal Server Error (The Catch-all)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalError(Exception ex, HttpServletRequest request) {
        var error = new ApiErrorResponse(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "An unexpected error occurred. Please contact support.",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}