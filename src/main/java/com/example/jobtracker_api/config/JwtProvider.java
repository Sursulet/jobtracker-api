package com.example.jobtracker_api.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gère la création, validation et extraction des données JWT.
 */
@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private final Key key;
    private final long validityInMilliseconds;

    // Stub de blacklist en mémoire (à remplacer par Redis ou autre)
    private final ConcurrentHashMap<String, Date> invalidatedTokens = new ConcurrentHashMap<>();

    public JwtProvider(
            @Value("${JWT_SECRET}") String secret,
            @Value("${jwt.expirationMs}") long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    /**
     * Génère un JWT avec subject, date d’émission et expiration.
     */
    public String createToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrait le nom d’utilisateur (subject) d’un token.
     */
    public String getUsername(String token) {
        return parseClaims(token).map(Claims::getSubject).orElse(null);
    }

    /**
     * Valide la signature, expiration, et vérifie l’absence en blacklist.
     */
    public boolean validateToken(String token) {
        Optional<Claims> claimsOpt = parseClaims(token);
        if (claimsOpt.isEmpty()) return false;

        if (invalidatedTokens.containsKey(token)) {
            logger.warn("Tentative d'utilisation d'un token invalide (blacklisté)");
            return false;
        }

        return true;
    }

    /**
     * Invalide un token pour empêcher sa réutilisation.
     */
    public void invalidate(String token) {
        parseClaims(token).ifPresent(claims -> {
            invalidatedTokens.put(token, claims.getExpiration());
            logger.info("Token invalidé jusqu’à {}", claims.getExpiration());
        });
    }

    private Optional<Claims> parseClaims(String token) {
        try {
            return Optional.of(Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody());
        } catch (ExpiredJwtException e) {
            logger.warn("Token expiré : {}", e.getMessage());
        } catch (JwtException e) {
            logger.error("Échec de parsing JWT : {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void purgeExpiredInvalidatedTokens() {
        Date now = new Date();
        int before = invalidatedTokens.size();
        invalidatedTokens.entrySet().removeIf(entry -> entry.getValue().before(now));
        int after = invalidatedTokens.size();

        if (before != after) {
            logger.info("Purge des tokens blacklistés : {} supprimés, {} restants", (before - after), after);
        }
    }
}