package com.medical.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.medical.api.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("medical-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    /**
     * Verifica el token y retorna el subject (username).
     *
     * @throws com.medical.api.infra.security.exceptions.TokenExpiredException si el token expiró
     * @throws com.medical.api.infra.security.exceptions.TokenInvalidException si el token es inválido
     */
    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("medical-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException e) {
            throw new com.medical.api.infra.security.exceptions.TokenExpiredException();
        } catch (JWTVerificationException e) {
            throw new com.medical.api.infra.security.exceptions.TokenInvalidException();
        }
    }

    private Instant generateExpiration() {
        return Instant.now().plus(Duration.ofHours(1));
    }
}
