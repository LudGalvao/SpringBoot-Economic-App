package com.economic.app.economic_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.app.economic_app.dto.LoginDTO;
import com.economic.app.economic_app.dto.RecuperacaoSenhaDTO;
import com.economic.app.economic_app.dto.RedefinicaoSenhaDTO;
import com.economic.app.economic_app.dto.TokenDTO;
import com.economic.app.economic_app.entity.Usuario;
import com.economic.app.economic_app.repository.UsuarioRepository;
import com.economic.app.economic_app.security.TokenAutenticacaoService;
import com.economic.app.economic_app.security.TokenRecuperacaoSenhaService;
import com.economic.app.economic_app.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticação", description = "API para autenticação e gerenciamento de senhas")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final TokenAutenticacaoService tokenService;
    private final TokenRecuperacaoSenhaService tokenRecuperacaoService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    
    @PostMapping("/login") //  "email": "ludadmin@gmail.com", "senha": "Lud123." (ADMIN)
    @Operation(summary = "Realizar login", 
               description = "Endpoint para autenticação de usuário e obtenção de token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                     content = @Content(schema = @Schema(implementation = TokenDTO.class))),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<TokenDTO> autenticar(@Valid @RequestBody LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getSenha()
                    )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenService.gerarToken(authentication);
            
            return ResponseEntity.ok(new TokenDTO(token));
        } catch (AuthenticationException e) {
            log.error("Erro na autenticação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/recuperar-senha")
    @Operation(summary = "Solicitar recuperação de senha", 
               description = "Endpoint para solicitar link de recuperação de senha por email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitação processada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro ao processar solicitação")
    })
    public ResponseEntity<?> solicitarRecuperacaoSenha(@Valid @RequestBody RecuperacaoSenhaDTO dto) {
        try {
            String email = dto.getEmail();
            log.info("Recebida solicitação de recuperação de senha para: {}", email);
            
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElse(null);
            
            if (usuario == null) {
                // Por segurança, não informamos se o email existe ou não
                log.info("Email não encontrado na base de dados: {}", email);
                return ResponseEntity.ok().build();
            }
            
            String token = tokenRecuperacaoService.gerarToken(email);
            log.info("Token gerado para recuperação de senha: {}", token);
            
            // Em ambiente de produção, definir a URL base correta da aplicação frontend
            String baseUrl = "http://localhost:3000";
            try {
                emailService.enviarEmailRecuperacaoSenha(email, token, baseUrl);
                log.info("Email de recuperação enviado com sucesso para: {}", email);
            } catch (Exception emailError) {
                log.error("Erro ao enviar email de recuperação: {}", emailError.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao enviar email de recuperação. Verifique as configurações de email.");
            }
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Erro ao processar solicitação de recuperação de senha: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/redefinir-senha")
    @Operation(summary = "Redefinir senha", 
               description = "Endpoint para redefinir a senha usando o token recebido por email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Token inválido ou senhas não coincidem"),
        @ApiResponse(responseCode = "500", description = "Erro ao redefinir senha")
    })
    public ResponseEntity<?> redefinirSenha(@Valid @RequestBody RedefinicaoSenhaDTO dto) {
        try {
            // Verificar se as senhas coincidem
            if (!dto.getNovaSenha().equals(dto.getConfirmacaoSenha())) {
                return ResponseEntity.badRequest()
                        .body("As senhas não coincidem");
            }
            
            // Validar o token de recuperação
            String email = tokenRecuperacaoService.validarToken(dto.getToken());
            if (email == null) {
                return ResponseEntity.badRequest()
                        .body("Token inválido ou expirado");
            }
            
            // Buscar o usuário pelo email
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElse(null);
            
            if (usuario == null) {
                return ResponseEntity.badRequest()
                        .body("Usuário não encontrado");
            }
            
            // Atualizar a senha
            usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
            usuarioRepository.save(usuario);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Erro ao redefinir senha: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
} 