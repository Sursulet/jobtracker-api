package com.aston.jobtracker_api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ─────────────────────────────────────────────────────────────────────────────
    // 401 Unauthorized (problèmes d'authentification)
    // ─────────────────────────────────────────────────────────────────────────────

    /** Mauvais identifiants / credentials invalides. */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex,
                                                              HttpServletRequest request) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), request);
    }

    /** Autres erreurs d'authentification Spring Security. */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex,
                                                              HttpServletRequest request) {
        log.warn("Authentication error: {}", ex.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), request);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // 403 Forbidden (droits insuffisants)
    // ─────────────────────────────────────────────────────────────────────────────

    /** Accès refusé (droits insuffisants). */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex,
                                                            HttpServletRequest request) {
        log.warn("Access denied: {}", ex.getMessage());
        return build(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), request);
    }

    /** Token de refresh invalide / expiré (erreur métier spécifique). */
    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponse> handleTokenRefresh(TokenRefreshException ex,
                                                            HttpServletRequest request) {
        log.warn("Refresh token error: {}", ex.getMessage());
        return build(HttpStatus.FORBIDDEN, "Refresh Token Error", ex.getMessage(), request);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // 400 Bad Request (validation)
    // ─────────────────────────────────────────────────────────────────────────────

    /** Erreurs de validation @Valid (DTO d'entrée). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex,
                                                   HttpServletRequest request) {
        // Corps d'erreur détaillant champ -> message
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Error");
        body.put("path", request.getRequestURI());

        Map<String, String> fieldErrors = new HashMap<>();
        for (var err : ex.getBindingResult().getAllErrors()) {
            String field = err instanceof FieldError fe ? fe.getField() : err.getObjectName();
            fieldErrors.put(field, err.getDefaultMessage());
        }
        body.put("messages", fieldErrors);
        log.debug("Validation errors: {}", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // 404 Not Found, 409 Conflict
    // ─────────────────────────────────────────────────────────────────────────────

    /** Entité non trouvée. */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex,
                                                        HttpServletRequest request) {
        log.info("Entity not found: {}", ex.getMessage());
        return build(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    /** Conflits d'intégrité (clé unique, FK, etc.). */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex,
                                                             HttpServletRequest request) {
        log.warn("Data integrity violation: {}", ex.getMostSpecificCause().getMessage());
        return build(HttpStatus.CONFLICT, "Data Integrity Violation",
                ex.getMostSpecificCause().getMessage(), request);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // 500 Internal Server Error
    // ─────────────────────────────────────────────────────────────────────────────

    /** Garde-fou pour toute exception non capturée plus haut. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
                                                       HttpServletRequest request) {
        log.error("Unexpected error", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "An unexpected error occurred.", request);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Utilitaire pour construire la réponse normalisée
    // ─────────────────────────────────────────────────────────────────────────────

    private ResponseEntity<ErrorResponse> build(HttpStatus status,
                                                String error,
                                                String message,
                                                HttpServletRequest request) {
        ErrorResponse payload = new ErrorResponse(
                Instant.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(payload);
    }
}
