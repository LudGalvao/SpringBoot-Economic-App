package com.economic.app.economic_app.service.email;

/**
 * Interface responsável por operações de envio de email
 */
public interface EmailService {
    
    /**
     * Envia um email
     * 
     * @param destinatario email do destinatário
     * @param assunto assunto do email
     * @param conteudo conteúdo do email
     */
    void enviarEmail(String destinatario, String assunto, String conteudo);
} 