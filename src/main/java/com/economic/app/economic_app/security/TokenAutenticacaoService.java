package com.economic.app.economic_app.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenAutenticacaoService {

    @Value("${app.security.jwt.auth.chave-secreta:chaveSuperSecretaParaAutenticacao}")
    private String chaveSecreta;

    @Value("${app.security.jwt.auth.expiracao:86400000}") // 24 horas por padrão
    private long tempoExpiracao;

    private javax.crypto.SecretKey chave;

    @PostConstruct
    protected void init() {
        this.chave = Keys.hmacShaKeyFor(chaveSecreta.getBytes());
    }

    /**
     * Gera um token JWT para o usuário autenticado.
     * 
     * @param authentication O objeto de autenticação do Spring Security
     * @return O token JWT gerado
     */
    public String gerarToken(Authentication authentication) {
        String username = authentication.getName();
        
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + tempoExpiracao);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);

        String token = Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(agora)
                .expiration(dataExpiracao)
                .signWith(chave)
                .compact();

        log.info("Token de autenticação gerado para o usuário: {}", username);
        return token;
    }

    /**
     * Obtém a autenticação do token JWT.
     * 
     * @param token O token JWT a ser validado
     * @return O objeto Authentication do Spring Security ou null se o token for inválido
     */
    public Authentication getAuthentication(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(chave)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();
            
            if (username == null) {
                return null;
            }
            
            Collection<? extends GrantedAuthority> authorities = 
                    obterAuthorities(claims);
            
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (Exception e) {
            log.error("Erro ao validar token de autenticação: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Extrai as permissões (authorities) do token JWT.
     */
    private Collection<? extends GrantedAuthority> obterAuthorities(Claims claims) {
        String authoritiesString = claims.get("authorities", String.class);
        
        if (authoritiesString == null || authoritiesString.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }
        
        return java.util.Arrays.stream(authoritiesString.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Valida um token JWT.
     * 
     * @param token O token JWT a ser validado
     * @return true se o token for válido, false caso contrário
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("Token inválido: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Extrai o username do token JWT.
     * 
     * @param token O token JWT
     * @return O username contido no token ou null se o token for inválido
     */
    public String extrairUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(chave)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            log.error("Erro ao extrair username do token: {}", e.getMessage());
            return null;
        }
    }
} 