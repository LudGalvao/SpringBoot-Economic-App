package com.economic.app.economic_app.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

    @Autowired
    private JwtConfig jwtConfig;

    public String gerarTokenRecuperacaoSenha(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tipo", "recuperacao-senha");
        return criarToken(claims, email, jwtConfig.getResetPasswordSecret(), jwtConfig.getResetPasswordExpiration());
    }

    public boolean validarTokenRecuperacaoSenha(String token) {
        try {
            return !isTokenExpirado(token, jwtConfig.getResetPasswordSecret());
        } catch (Exception e) {
            return false;
        }
    }

    public String obterEmailDoToken(String token) {
        return obterClaim(token, jwtConfig.getResetPasswordSecret(), Claims::getSubject);
    }

    private String criarToken(Map<String, Object> claims, String subject, String secret, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    private <T> T obterClaim(String token, String secret, Function<Claims, T> claimsResolver) {
        final Claims claims = obterTodasClaims(token, secret);
        return claimsResolver.apply(claims);
    }

    private Claims obterTodasClaims(String token, String secret) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpirado(String token, String secret) {
        final Date expiration = obterClaim(token, secret, Claims::getExpiration);
        return expiration.before(new Date());
    }
} 