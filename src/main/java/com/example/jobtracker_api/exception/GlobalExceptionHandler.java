package com.example.jobtracker_api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Pour gérer les erreurs REST et renvoyer un format JSON uniforme.
 */
// @Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

// ─────────────────────────────────────────────────────────────────────────────
// 401 Unauthorized (problèmes d'authentification)
// ─────────────────────────────────────────────────────────────────────────────

    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<Map<String, Object>> handleAuth(Exception ex, HttpServletRequest request) {
        log.warn("Authentication error: {}", ex.getMessage());
        return body(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), request.getRequestURI());
    }

// ─────────────────────────────────────────────────────────────────────────────
// 403 Forbidden (droits insuffisants, refresh token refusé)
// ─────────────────────────────────────────────────────────────────────────────

    @ExceptionHandler({AccessDeniedException.class, TokenRefreshException.class})
    public ResponseEntity<Map<String, Object>> handleForbidden(Exception ex, HttpServletRequest request) {
        log.warn("Forbidden: {}", ex.getMessage());
        String label = (ex instanceof TokenRefreshException) ? "Refresh Token Error" : "Forbidden";
        return body(HttpStatus.FORBIDDEN, label, ex.getMessage(), request.getRequestURI());
    }

// ─────────────────────────────────────────────────────────────────────────────
// 400 Bad Request (erreurs de validation)
// ─────────────────────────────────────────────────────────────────────────────

    /**

     Erreurs de validation sur les DTO annotés avec @Valid (MethodArgumentNotValidException).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex,
                                                                HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (var error : ex.getBindingResult().getAllErrors()) {
            String field = (error instanceof FieldError fe) ? fe.getField() : error.getObjectName();
            fieldErrors.put(field, error.getDefaultMessage());
        }
        Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Validation Error", request.getRequestURI());
        body.put("messages", fieldErrors);
        log.debug("Validation errors: {}", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**

     Erreurs de validation type contraintes (ex: @NotBlank sur @RequestParam, etc.).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraint(ConstraintViolationException ex,
                                                                HttpServletRequest request) {
        Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Validation Error", request.getRequestURI());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

// ─────────────────────────────────────────────────────────────────────────────
// 404 & 409
// ─────────────────────────────────────────────────────────────────────────────

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.info("Not found: {}", ex.getMessage());
        return body(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleIntegrity(DataIntegrityViolationException ex,
                                                               HttpServletRequest request) {
        String msg = (ex.getMostSpecificCause() != null) ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        log.warn("Data integrity violation: {}", msg);
        return body(HttpStatus.CONFLICT, "Data Integrity Violation", msg, request.getRequestURI());
    }

// ─────────────────────────────────────────────────────────────────────────────
// 500
// ─────────────────────────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error", ex);
        return body(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred.", request.getRequestURI());
    }

// ─────────────────────────────────────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────────────────────────────────────

    private ResponseEntity<Map<String, Object>> body(HttpStatus status, String error, String message, String path) {
        return ResponseEntity.status(status).body(base(status, error, path, message));
    }

    private Map<String, Object> base(HttpStatus status, String error, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("path", path);
        return body;
    }

    private Map<String, Object> base(HttpStatus status, String error, String path, String message) {
        Map<String, Object> body = base(status, error, path);
        body.put("message", message);
        return body;
    }
}

