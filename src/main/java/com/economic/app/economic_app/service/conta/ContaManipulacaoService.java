package com.economic.app.economic_app.service.conta;

import com.economic.app.economic_app.dto.ContaBancariaDTO;
import com.economic.app.economic_app.entity.Conta;

/**
 * Interface responsável por operações de manipulação de contas
 */
public interface ContaManipulacaoService {
    
    /**
     * Cadastra uma nova conta bancária para o usuário autenticado
     * 
     * @param contaBancariaDTO dados da conta bancária
     * @return a conta bancária cadastrada
     */
    Conta cadastrarConta(ContaBancariaDTO contaBancariaDTO);
    
    /**
     * Atualiza uma conta bancária
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param id id da conta
     * @param contaBancariaDTO dados da conta bancária
     * @return a conta bancária atualizada
     */
    Conta atualizarConta(String id, ContaBancariaDTO contaBancariaDTO);
    
    /**
     * Atualiza uma conta bancária pela descrição
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param descricao descrição da conta
     * @param contaBancariaDTO dados da conta bancária
     * @return a conta bancária atualizada
     */
    Conta atualizarContaPorDescricao(String descricao, ContaBancariaDTO contaBancariaDTO);
    
    /**
     * Remove uma conta bancária
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param id id da conta
     */
    void removerConta(String id);
    
    /**
     * Remove uma conta bancária pela descrição
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param descricao descrição da conta
     */
    void removerContaPorDescricao(String descricao);
} 