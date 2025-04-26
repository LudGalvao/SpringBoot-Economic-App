package com.nflash.nfespring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nflash.nfespring.dto.SolicitarResetSenhaDTO;
import com.nflash.nfespring.dto.NovaSenhaDTO;
import com.nflash.nfespring.service.EmailService;
import com.nflash.nfespring.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/reset-senha")
@Tag(name = "Reset de senha", description = "API para gerenciamento de reset de senha")
public class ResetSenhaController {

    private final UsuarioService usuarioService;
    private final EmailService emailService;

    public ResetSenhaController(UsuarioService usuarioService, EmailService emailService){
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    @PostMapping("/solicitar")
    @Operation(summary = "Solicitar reset de senha", description = "Enviar um token de reset para o email do usuário")
    @ApiResponse(responseCode = "200", description = "Solicitação de reset enviada com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<?> solicitarResetSenha(SolicitarResetSenhaDTO dto){
        try{
            String token = usuarioService.gerarTokenResetSenha(dto.getEmail());

            emailService.enviarEmailResetSenha(dto.getEmail(), token);

            return ResponseEntity.ok("Solicitação de reset de senha enviada com sucesso. Verifique seu email");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
    } 
    }

    @PostMapping("/resetar")
    @Operation(summary = "Resetar senha", description = "Redefine a senha do usuário com o token fornecido")
    @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso")
    @ApiResponse(responseCode = "404", description = "Token inválido ou expirado")
    public ResponseEntity<?> resetarSenha(NovaSenhaDTO dto){
        try{
            usuarioService.resetarSenha(dto.getToken(), dto.getNovaSenha());
            return ResponseEntity.ok("Senha redefinida com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
