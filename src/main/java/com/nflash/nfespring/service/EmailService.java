package com.nflash.nfespring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String remetente;
    
    @Value("${app.frontend.url}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailResetSenha(String destinatario, String token) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject("Redefinição de Senha");
        
        String resetUrl = frontendUrl + "/reset-senha?token=" + token;
        
        mensagem.setText("Olá,\n\n" +
                "Você solicitou a redefinição de sua senha. Clique no link abaixo para criar uma nova senha:\n\n" +
                resetUrl + "\n\n" +
                "Este link expira em 24 horas.\n\n" +
                "Se você não solicitou esta alteração, por favor ignore este email.");
        
        mailSender.send(mensagem);
    }
}