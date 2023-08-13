package com.voll.med.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.voll.med.api.domain.usuario.Usuario;

import jakarta.validation.constraints.NotBlank;

@Service
public class TokenService {

    private static final String INVALID_TOKEN_ERROR_MSG = "Token JWT Inv\u00E1lido";

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String gerarToken(Usuario usuario) {
        try {
            var algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                .withIssuer("API Voll.med")
                .withSubject(usuario.getLogin())
                .withExpiresAt(dataExpiracao())
                //.withClaim("id", usuario.getId())
                .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String getSubject (@NotBlank(message = INVALID_TOKEN_ERROR_MSG) String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer("API Voll.med")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException(INVALID_TOKEN_ERROR_MSG);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
