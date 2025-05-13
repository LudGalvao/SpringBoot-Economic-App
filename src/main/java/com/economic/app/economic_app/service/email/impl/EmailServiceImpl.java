package com.economic.app.economic_app.service.email.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.economic.app.economic_app.service.email.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username:noreply@economic-app.com}")
    private String remetente;
    
    @Value("${spring.mail.host:unknown}")
    private String mailHost;
    
    @Value("${spring.mail.port:0}")
    private String mailPort;
    
    @Value("${spring.mail.properties.mail.smtp.auth:false}")
    private String smtpAuth;
    
    @Override
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
            throw new RuntimeException("Falha ao enviar email: " + e.getMessage(), e);
        }
    }
} 