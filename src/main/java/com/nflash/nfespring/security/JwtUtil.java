package com.nflash.nfespring.security;

import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY;

    public JwtUtil(){
        // vai gerar uma chave aleatória de 256 bits
        byte[] keyBytes = new byte[32]; 
        new SecureRandom().nextBytes(keyBytes);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generatedToken(String email){
        return Jwts.builder()
        .subject(email)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(SECRET_KEY)
        .compact();
    }

    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token){
        return getClaims(token).getExpiration().after(new Date());
    }

    private Claims getClaims(String token){
        return Jwts.parser()
        .decryptWith(SECRET_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }
}
