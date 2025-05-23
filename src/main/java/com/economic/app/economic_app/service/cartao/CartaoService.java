package com.economic.app.economic_app.service.cartao;

import java.util.List;

import com.economic.app.economic_app.dto.CadastrarCartaoDTO;
import com.economic.app.economic_app.entity.Cartao;

public interface CartaoService {
    
    /**
     * Cadastra um novo cartão para o usuário logado
     * 
     * @param dto Dados do cartão a ser cadastrado
     * @return O cartão cadastrado
     */
    Cartao cadastrarCartao(CadastrarCartaoDTO dto);
    
    /**
     * Obtém todos os cartões do usuário logado
     * 
     * @return Lista de cartões do usuário
     */
    List<Cartao> obterCartoesDoUsuario();
    
    /**
     * Obtém um cartão pelo ID, verificando se pertence ao usuário logado
     * 
     * @param id ID do cartão
     * @return O cartão encontrado
     */
    Cartao obterCartaoPorId(String id);
    
    /**
     * Exclui um cartão, verificando se pertence ao usuário logado
     * 
     * @param id ID do cartão a ser excluído
     */
    void excluirCartao(String id);
} 