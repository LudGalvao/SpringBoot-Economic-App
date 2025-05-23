package com.economic.app.economic_app.service.cartao.impl;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.economic.app.economic_app.dto.CadastrarCartaoDTO;
import com.economic.app.economic_app.entity.Cartao;
import com.economic.app.economic_app.entity.Usuario;
import com.economic.app.economic_app.exception.RecursoNaoEncontradoException;
import com.economic.app.economic_app.exception.ValidacaoException;
import com.economic.app.economic_app.repository.CartaoRepository;
import com.economic.app.economic_app.repository.UsuarioRepository;
import com.economic.app.economic_app.service.cartao.CartaoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {
    
    private final CartaoRepository cartaoRepository;
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public Cartao cadastrarCartao(CadastrarCartaoDTO dto) {
        Usuario usuario = obterUsuarioLogado();
        
        // Verificar se já existe um cartão com este nome para o usuário
        if (cartaoRepository.existsByNomeAndUsuarioId(dto.getNome(), usuario.getId())) {
            throw new ValidacaoException("Já existe um cartão com este nome cadastrado para o usuário");
        }
        
        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setBandeira(dto.getBandeira());
        cartao.setBanco(dto.getBanco());
        cartao.setDiaFechamento(dto.getDiaFechamento());
        cartao.setDiaVencimento(dto.getDiaVencimento());
        cartao.setLimite(dto.getLimite());
        cartao.setUsuarioId(usuario.getId());
        
        return cartaoRepository.save(cartao);
    }
    
    @Override
    public List<Cartao> obterCartoesDoUsuario() {
        Usuario usuario = obterUsuarioLogado();
        return cartaoRepository.findByUsuarioId(usuario.getId());
    }
    
    @Override
    public Cartao obterCartaoPorId(String id) {
        Usuario usuario = obterUsuarioLogado();
        
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado"));
        
        // Verificar se o cartão pertence ao usuário logado
        if (!cartao.getUsuarioId().equals(usuario.getId())) {
            throw new AccessDeniedException("Você não tem permissão para acessar este cartão");
        }
        
        return cartao;
    }
    
    @Override
    public void excluirCartao(String id) {
        Cartao cartao = obterCartaoPorId(id);
        cartaoRepository.delete(cartao);
    }
    
    /**
     * Obtém o usuário atualmente autenticado
     * 
     * @return O usuário logado
     */
    private Usuario obterUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        return usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }
} 