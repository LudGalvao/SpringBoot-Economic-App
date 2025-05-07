package com.economic.app.economic_app.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenRecuperacaoSenhaService {

    @Value("${app.security.jwt.recuperacao-senha.chave-secreta:chaveSuperSecretaParaRecuperacaoDeSenha}")
    private String chaveSecreta;

    @Value("${app.security.jwt.recuperacao-senha.expiracao:3600000}") // 1 hora por padrão
    private long tempoExpiracao;

    private javax.crypto.SecretKey chave;

    @PostConstruct
    protected void init() {
        this.chave = Keys.hmacShaKeyFor(chaveSecreta.getBytes());
    }

    /**
     * Gera um token JWT para recuperação de senha.
     * 
     * @param email O email do usuário que solicitou a recuperação de senha
     * @return O token JWT gerado
     */
    public String gerarToken(String email) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + tempoExpiracao);

        String token = Jwts.builder()
                .subject(email)
                .claim("tipo", "recuperacao-senha")
                .issuedAt(agora)
                .expiration(dataExpiracao)
                .signWith(chave)
                .compact();

        log.info("Token de recuperação de senha gerado para o email: {}", email);
        return token;
    }

    /**
     * Valida um token JWT de recuperação de senha.
     * 
     * @param token O token JWT a ser validado
     * @return O email do usuário contido no token, ou null se o token for inválido
     */
    public String validarToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(chave)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String tipo = claims.get("tipo", String.class);
            if (!"recuperacao-senha".equals(tipo)) {
                log.warn("Token não é do tipo recuperação de senha");
                return null;
            }

            return claims.getSubject();
        } catch (Exception e) {
            log.error("Erro ao validar token de recuperação de senha: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Verifica se um token de recuperação de senha é válido.
     * 
     * @param token O token JWT a ser verificado
     * @return true se o token for válido, false caso contrário
     */
    public boolean isTokenValido(String token) {
        return validarToken(token) != null;
    }
} 