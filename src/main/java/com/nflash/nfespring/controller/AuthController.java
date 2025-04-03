package com.nflash.nfespring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nflash.nfespring.dto.UsuarioLoginDTO;
import com.nflash.nfespring.dto.UsuarioRegistroDTO;
import com.nflash.nfespring.entity.Usuario;
import com.nflash.nfespring.security.JwtUtil;
import com.nflash.nfespring.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioRegistroDTO usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.getUsername());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(usuarioDto.getSenha());
        usuario.setCpf(usuarioDto.getCpf());
        usuario.setRazaoSocial(usuarioDto.getRazaoSocial());
        usuario.setNomeFantasia(usuarioDto.getNomeFantasia());
        usuario.setTelefone(usuarioDto.getTelefone());

        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String enderecoJson = objectMapper.writeValueAsString(usuarioDto.getEndereco());
            usuario.setEndereco(enderecoJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar o endereço: " + e.getMessage());
        }

        usuarioService.registrarUsuario(usuario);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                usuarioLoginDTO.getEmail(), 
                usuarioLoginDTO.getSenha()
            )  
        );

        UserDetails userDetails = usuarioService.loadUserByUsername(usuarioLoginDTO.getEmail());
        String token = jwtUtil.generatedToken(userDetails.getUsername());

        return ResponseEntity.ok(token);
    }
}