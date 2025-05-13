package com.economic.app.economic_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.app.economic_app.dto.RecuperacaoSenhaDTO;
import com.economic.app.economic_app.dto.RedefinicaoSenhaDTO;
import com.economic.app.economic_app.service.auth.PasswordRecoveryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth/password")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Recuperação de Senha", description = "API para recuperação e redefinição de senha")
public class PasswordRecoveryController {
    
    private final PasswordRecoveryService passwordRecoveryService;
    
    @PostMapping("/recuperar")
    @Operation(summary = "Solicitar recuperação de senha", 
               description = "Endpoint para solicitar recuperação de senha através do email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email de recuperação enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Email não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro ao enviar email")
    })
    public ResponseEntity<String> solicitarRecuperacaoSenha(@Valid @RequestBody RecuperacaoSenhaDTO dto) {
        try {
            passwordRecoveryService.iniciarRecuperacaoSenha(dto.getEmail());
            return ResponseEntity.ok("Email de recuperação enviado com sucesso");
        } catch (IllegalArgumentException e) {
            log.error("Erro ao solicitar recuperação de senha: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao solicitar recuperação de senha: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Erro ao processar solicitação");
        }
    }
    
    @PutMapping("/redefinir")
    @Operation(summary = "Redefinir senha", 
               description = "Endpoint para redefinir senha usando token de recuperação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Token inválido ou senhas não coincidem"),
        @ApiResponse(responseCode = "500", description = "Erro ao redefinir senha")
    })
    public ResponseEntity<String> redefinirSenha(@Valid @RequestBody RedefinicaoSenhaDTO dto) {
        try {
            passwordRecoveryService.redefinirSenha(dto);
            return ResponseEntity.ok("Senha redefinida com sucesso");
        } catch (IllegalArgumentException e) {
            log.error("Erro ao redefinir senha: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao redefinir senha: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Erro ao processar solicitação");
        }
    }
} 