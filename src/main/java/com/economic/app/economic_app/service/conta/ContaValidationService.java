package com.economic.app.economic_app.service.conta;

import com.economic.app.economic_app.dto.ContaBancariaDTO;

/**
 * Interface responsável por validações de contas
 */
public interface ContaValidationService {
    
    /**
     * Valida os dados de uma conta bancária
     * 
     * @param contaBancariaDTO dados da conta a serem validados
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    void validarConta(ContaBancariaDTO contaBancariaDTO);
    
    /**
     * Valida se uma conta pertence a um usuário
     * 
     * @param contaId id da conta
     * @param usuarioId id do usuário
     * @throws IllegalArgumentException se a conta não pertencer ao usuário
     */
    void validarPropriedade(String contaId, String usuarioId);
    
    /**
     * Valida se uma instituição financeira é válida
     * 
     * @param instituicaoFinanceira nome da instituição financeira
     * @throws IllegalArgumentException se a instituição financeira for inválida
     */
    void validarInstituicaoFinanceira(String instituicaoFinanceira);
} 