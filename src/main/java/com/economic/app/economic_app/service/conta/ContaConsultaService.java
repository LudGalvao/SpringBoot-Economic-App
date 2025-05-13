package com.economic.app.economic_app.service.conta;

import java.util.List;
import com.economic.app.economic_app.entity.Conta;

/**
 * Interface responsável por operações de consulta de contas
 */
public interface ContaConsultaService {
    
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
} 