package com.economic.app.economic_app.service.auth;

import com.economic.app.economic_app.dto.RedefinicaoSenhaDTO;

/**
 * Interface responsável por operações de recuperação de senha
 */
public interface PasswordRecoveryService {
    
    /**
     * Inicia o processo de recuperação de senha
     * 
     * @param email email do usuário
     * @throws IllegalArgumentException se o email não for encontrado
     */
    void iniciarRecuperacaoSenha(String email);
    
    /**
     * Redefine a senha do usuário
     * 
     * @param dto dados para redefinição de senha
     * @throws IllegalArgumentException se o token for inválido ou as senhas não coincidirem
     */
    void redefinirSenha(RedefinicaoSenhaDTO dto);
} 