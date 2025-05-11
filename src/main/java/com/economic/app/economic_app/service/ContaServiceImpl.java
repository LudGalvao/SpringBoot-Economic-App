package com.economic.app.economic_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.economic.app.economic_app.dto.ContaBancariaDTO;
import com.economic.app.economic_app.entity.Conta;
import com.economic.app.economic_app.entity.Usuario;
import com.economic.app.economic_app.exception.RecursoNaoEncontradoException;
import com.economic.app.economic_app.repository.ContaRepository;
import com.economic.app.economic_app.repository.UsuarioRepository;

@Service
public class ContaServiceImpl implements ContaService {

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public Conta cadastrarConta(ContaBancariaDTO contaBancariaDTO) {
        String usuarioId = getUsuarioIdAutenticado();
        
        Conta conta = new Conta();
        conta.setUsuarioId(usuarioId);
        conta.setInstituicaoFinanceira(contaBancariaDTO.getInstituicaoFinanceira());
        conta.setDescricao(contaBancariaDTO.getDescricao());
        conta.setTipo(contaBancariaDTO.getTipo());
        conta.setCor(contaBancariaDTO.getCor());
        
        return contaRepository.save(conta);
    }
    
    @Override
    public List<Conta> buscarContasPorUsuarioAutenticado() {
        String usuarioId = getUsuarioIdAutenticado();
        return contaRepository.findByUsuarioId(usuarioId);
    }
    
    @Override
    public Conta buscarContaPorId(String id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada com o ID: " + id));
        
        // Verificar se a conta pertence ao usuário autenticado
        String usuarioId = getUsuarioIdAutenticado();
        if (!conta.getUsuarioId().equals(usuarioId)) {
            throw new RecursoNaoEncontradoException("Conta não encontrada para o usuário autenticado");
        }
        
        return conta;
    }
    
    @Override
    public Conta buscarContaPorDescricao(String descricao) {
        String usuarioId = getUsuarioIdAutenticado();
        return contaRepository.findByDescricaoAndUsuarioId(descricao, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada com a descrição: " + descricao));
    }
    
    @Override
    public Conta buscarContaPorInstituicaoFinanceira(String instituicaoFinanceira) {
        String usuarioId = getUsuarioIdAutenticado();
        return contaRepository.findByInstituicaoFinanceiraAndUsuarioId(instituicaoFinanceira, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada com a instituição financeira: " + instituicaoFinanceira));
    }
    
    @Override
    public List<Conta> buscarContasPorInstituicaoFinanceiraContendo(String instituicaoFinanceira) {
        String usuarioId = getUsuarioIdAutenticado();
        List<Conta> contas = contaRepository.findByInstituicaoFinanceiraContainingAndUsuarioId(instituicaoFinanceira, usuarioId);
        
        if (contas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma conta encontrada contendo a instituição financeira: " + instituicaoFinanceira);
        }
        
        return contas;
    }
    
    @Override
    public Conta atualizarConta(String id, ContaBancariaDTO contaBancariaDTO) {
        Conta contaExistente = buscarContaPorId(id);
        
        contaExistente.setInstituicaoFinanceira(contaBancariaDTO.getInstituicaoFinanceira());
        contaExistente.setDescricao(contaBancariaDTO.getDescricao());
        contaExistente.setTipo(contaBancariaDTO.getTipo());
        contaExistente.setCor(contaBancariaDTO.getCor());
        
        return contaRepository.save(contaExistente);
    }
    
    @Override
    public Conta atualizarContaPorDescricao(String descricao, ContaBancariaDTO contaBancariaDTO) {
        Conta contaExistente = buscarContaPorDescricao(descricao);
        
        contaExistente.setInstituicaoFinanceira(contaBancariaDTO.getInstituicaoFinanceira());
        contaExistente.setDescricao(contaBancariaDTO.getDescricao());
        contaExistente.setTipo(contaBancariaDTO.getTipo());
        contaExistente.setCor(contaBancariaDTO.getCor());
        
        return contaRepository.save(contaExistente);
    }
    
    @Override
    public Conta atualizarContaPorInstituicaoFinanceira(String instituicaoFinanceira, ContaBancariaDTO contaBancariaDTO) {
        Conta contaExistente = buscarContaPorInstituicaoFinanceira(instituicaoFinanceira);
        
        contaExistente.setInstituicaoFinanceira(contaBancariaDTO.getInstituicaoFinanceira());
        contaExistente.setDescricao(contaBancariaDTO.getDescricao());
        contaExistente.setTipo(contaBancariaDTO.getTipo());
        contaExistente.setCor(contaBancariaDTO.getCor());
        
        return contaRepository.save(contaExistente);
    }
    
    @Override
    public void removerConta(String id) {
        Conta conta = buscarContaPorId(id);
        contaRepository.delete(conta);
    }
    
    @Override
    public void removerContaPorDescricao(String descricao) {
        Conta conta = buscarContaPorDescricao(descricao);
        contaRepository.delete(conta);
    }
    
    @Override
    public void removerContaPorInstituicaoFinanceira(String instituicaoFinanceira) {
        Conta conta = buscarContaPorInstituicaoFinanceira(instituicaoFinanceira);
        contaRepository.delete(conta);
    }
    
    /**
     * Obtém o ID do usuário autenticado a partir do contexto de segurança
     * 
     * @return ID do usuário autenticado
     * @throws RuntimeException se o usuário não estiver autenticado
     */
    private String getUsuarioIdAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }
        
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + email));
        
        return usuario.getId();
    }
}