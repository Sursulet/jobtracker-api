package com.example.jobtracker_api.exception;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String token, String message) {
        super(String.format("Erreur pour le token [%s] : %s", token, message));
    }
}
