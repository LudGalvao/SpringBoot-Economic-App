package com.economic.app.economic_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.app.economic_app.dto.LoginDTO;
import com.economic.app.economic_app.dto.TokenDTO;
import com.economic.app.economic_app.security.TokenAutenticacaoService;

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
@Tag(name = "Autenticação", description = "API para autenticação de usuários")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final TokenAutenticacaoService tokenService;
    
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
            String email = authentication.getName();
            
            log.info("Usuário autenticado com sucesso: {}", email);
            return ResponseEntity.ok(new TokenDTO(token, email));
        } catch (AuthenticationException e) {
            log.error("Erro na autenticação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
} 