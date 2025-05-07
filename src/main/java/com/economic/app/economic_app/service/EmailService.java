package com.economic.app.economic_app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username:noreply@economic-app.com}")
    private String remetente;
    
    @Value("${spring.mail.host:unknown}")
    private String mailHost;
    
    @Value("${spring.mail.port:0}")
    private String mailPort;
    
    @Value("${spring.mail.properties.mail.smtp.auth:false}")
    private String smtpAuth;
    
    /**
     * Envia um email de recuperação de senha
     * 
     * @param destinatario o email do destinatário
     * @param token o token de recuperação de senha
     * @param baseUrl a URL base da aplicação
     */
    public void enviarEmailRecuperacaoSenha(String destinatario, String token, String baseUrl) {
        String assunto = "Recuperação de Senha - Economic App";
        String url = baseUrl + "/redefinir-senha?token=" + token;
        
        String conteudo = "Olá,\n\n" +
                "Você solicitou a recuperação de senha para a sua conta no Economic App.\n\n" +
                "Para redefinir sua senha, clique no link abaixo:\n" +
                url + "\n\n" +
                "Se você não solicitou esta recuperação, por favor ignore este email.\n\n" +
                "Este link irá expirar em 1 hora.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Economic App";
        
        enviarEmail(destinatario, assunto, conteudo);
    }
    
    /**
     * Método genérico para envio de emails
     * 
     * @param destinatario o email do destinatário
     * @param assunto o assunto do email
     * @param conteudo o conteúdo do email
     */
    public void enviarEmail(String destinatario, String assunto, String conteudo) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(remetente);
            mensagem.setTo(destinatario);
            mensagem.setSubject(assunto);
            mensagem.setText(conteudo);
            
            log.info("Tentando enviar email para {} usando remetente {} via servidor {}:{}", 
                    destinatario, remetente, mailHost, mailPort);
            log.info("Autenticação SMTP ativada: {}", smtpAuth);
            mailSender.send(mensagem);
            log.info("Email enviado com sucesso para {}", destinatario);
        } catch (Exception e) {
            log.error("Erro ao enviar email para {}: {}", destinatario, e.getMessage());
            log.error("Detalhes do erro: ", e);
            // Lançar exceção para que o controller saiba que houve um erro
            throw new RuntimeException("Falha ao enviar email: " + e.getMessage(), e);
        }
    }
} 