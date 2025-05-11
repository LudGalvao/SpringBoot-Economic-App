package com.economic.app.economic_app.service;

import java.util.List;

import com.economic.app.economic_app.dto.ContaBancariaDTO;
import com.economic.app.economic_app.entity.Conta;

public interface ContaService {
    
    /**
     * Cadastra uma nova conta bancária para o usuário autenticado
     * 
     * @param contaBancariaDTO dados da conta bancária
     * @return a conta bancária cadastrada
     */
    Conta cadastrarConta(ContaBancariaDTO contaBancariaDTO);
    
    /**
     * Busca todas as contas do usuário autenticado
     * 
     * @return lista de contas do usuário
     */
    List<Conta> buscarContasPorUsuarioAutenticado();
    
    /**
     * Busca uma conta pelo id
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param id id da conta
     * @return a conta encontrada
     */
    Conta buscarContaPorId(String id);
    
    /**
     * Busca uma conta pela descrição
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param descricao descrição da conta
     * @return a conta encontrada
     */
    Conta buscarContaPorDescricao(String descricao);
    
    /**
     * Busca uma conta pela instituição financeira
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param instituicaoFinanceira nome da instituição financeira
     * @return a conta encontrada
     */
    Conta buscarContaPorInstituicaoFinanceira(String instituicaoFinanceira);
    
    /**
     * Busca contas pela instituição financeira (busca parcial)
     * Verifica se as contas pertencem ao usuário autenticado
     * 
     * @param instituicaoFinanceira parte do nome da instituição financeira
     * @return lista de contas encontradas
     */
    List<Conta> buscarContasPorInstituicaoFinanceiraContendo(String instituicaoFinanceira);
    
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
     * Atualiza uma conta bancária pela instituição financeira
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param instituicaoFinanceira nome da instituição financeira
     * @param contaBancariaDTO dados da conta bancária
     * @return a conta bancária atualizada
     */
    Conta atualizarContaPorInstituicaoFinanceira(String instituicaoFinanceira, ContaBancariaDTO contaBancariaDTO);
    
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
    
    /**
     * Remove uma conta bancária pela instituição financeira
     * Verifica se a conta pertence ao usuário autenticado
     * 
     * @param instituicaoFinanceira nome da instituição financeira
     */
    void removerContaPorInstituicaoFinanceira(String instituicaoFinanceira);
}