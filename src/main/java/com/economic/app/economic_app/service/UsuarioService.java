package com.economic.app.economic_app.service;

import java.util.List;
import java.util.Optional;

import com.economic.app.economic_app.dto.AtualizarUsuarioDTO;
import com.economic.app.economic_app.dto.CadastrarUsuarioDTO;
import com.economic.app.economic_app.entity.Usuario;

public interface UsuarioService {
    
    /**
     * Cadastra um novo usuário
     * 
     * @param dto Dados para cadastro do usuário
     * @return O usuário cadastrado
     */
    Usuario cadastrar(CadastrarUsuarioDTO dto);
    
    /**
     * Busca um usuário pelo ID
     * 
     * @param id ID do usuário
     * @return Optional contendo o usuário, se encontrado
     */
    Optional<Usuario> buscarPorId(String id);
    
    /**
     * Busca um usuário pelo email
     * 
     * @param email Email do usuário
     * @return Optional contendo o usuário, se encontrado
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Lista todos os usuários
     * 
     * @return Lista de usuários
     */
    List<Usuario> listarTodos();
    
    /**
     * Atualiza a senha de um usuário
     * 
     * @param id ID do usuário
     * @param novaSenha Nova senha (não criptografada)
     * @return true se a senha foi atualizada com sucesso, false caso contrário
     */
    boolean atualizarSenha(String id, String novaSenha);
    
    /**
     * Atualiza os dados de um usuário
     * 
     * @param id ID do usuário
     * @param dto Dados para atualização
     * @return Optional contendo o usuário atualizado, ou Optional vazio se o usuário não for encontrado
     */
    Optional<Usuario> atualizar(String id, AtualizarUsuarioDTO dto);
    
    /**
     * Remove um usuário pelo ID
     * 
     * @param id ID do usuário
     * @return true se o usuário foi removido com sucesso, false caso contrário
     */
    boolean remover(String id);
} 