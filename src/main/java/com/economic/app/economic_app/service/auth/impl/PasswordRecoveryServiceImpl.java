package com.economic.app.economic_app.service.auth.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.economic.app.economic_app.dto.RedefinicaoSenhaDTO;
import com.economic.app.economic_app.entity.Usuario;
import com.economic.app.economic_app.repository.UsuarioRepository;
import com.economic.app.economic_app.security.TokenRecuperacaoSenhaService;
import com.economic.app.economic_app.service.auth.PasswordRecoveryService;
import com.economic.app.economic_app.service.email.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRecuperacaoSenhaService tokenRecuperacaoService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void iniciarRecuperacaoSenha(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));

        String token = tokenRecuperacaoService.gerarToken(email);
        
        try {
            String conteudoEmail = String.format(
                "Olá %s,\n\n" +
                "Você solicitou a recuperação de senha. Use o token abaixo para redefinir sua senha:\n\n" +
                "%s\n\n" +
                "Este token é válido por 30 minutos.\n\n" +
                "Se você não solicitou a recuperação de senha, ignore este email.",
                usuario.getNomePreferencia(), token);

            emailService.enviarEmail(
                email,
                "Recuperação de Senha - Economic App",
                conteudoEmail
            );
            
            log.info("Email de recuperação enviado para: {}", email);
        } catch (Exception e) {
            log.error("Erro ao enviar email de recuperação: {}", e.getMessage());
            throw new RuntimeException("Erro ao enviar email de recuperação", e);
        }
    }

    @Override
    public void redefinirSenha(RedefinicaoSenhaDTO dto) {
        if (!dto.getNovaSenha().equals(dto.getConfirmacaoSenha())) {
            throw new IllegalArgumentException("As senhas não coincidem");
        }

        String email = tokenRecuperacaoService.validarToken(dto.getToken());
        if (email == null) {
            throw new IllegalArgumentException("Token inválido ou expirado");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuarioRepository.save(usuario);
        
        log.info("Senha redefinida com sucesso para o usuário: {}", email);
    }
} 