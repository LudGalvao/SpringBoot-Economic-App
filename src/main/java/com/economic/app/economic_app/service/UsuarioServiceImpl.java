package com.economic.app.economic_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.economic.app.economic_app.dto.AtualizarUsuarioDTO;
import com.economic.app.economic_app.dto.CadastrarUsuarioDTO;
import com.economic.app.economic_app.entity.Usuario;
import com.economic.app.economic_app.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public Usuario cadastrar(CadastrarUsuarioDTO dto) {
        // Verificar se já existe um usuário com este email
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setNomePreferencia(dto.getNomePreferencia());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSexo(dto.getSexo());
        usuario.setNacionalidade(dto.getNacionalidade());
        usuario.setCidade(dto.getCidade());
        usuario.setEstado(dto.getEstado());
        usuario.setEscolaridade(dto.getEscolaridade());
        usuario.setProfissao(dto.getProfissao());
        usuario.setFaixaRendaMensal(dto.getFaixaRendaMensal());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        
        Usuario usuarioCadastrado = usuarioRepository.save(usuario);
        log.info("Usuário cadastrado com sucesso: {}", usuarioCadastrado.getEmail());
        
        return usuarioCadastrado;
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean atualizarSenha(String id, String novaSenha) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        
        if (optUsuario.isEmpty()) {
            return false;
        }
        
        Usuario usuario = optUsuario.get();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        
        log.info("Senha atualizada para o usuário: {}", usuario.getEmail());
        return true;
    }
    
    @Override
    public Optional<Usuario> atualizar(String id, AtualizarUsuarioDTO dto) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        
        if (optUsuario.isEmpty()) {
            return Optional.empty();
        }
        
        Usuario usuario = optUsuario.get();
        
        // Atualiza apenas os campos não nulos
        if (dto.getNomeCompleto() != null) {
            usuario.setNomeCompleto(dto.getNomeCompleto());
        }
        
        if (dto.getNomePreferencia() != null) {
            usuario.setNomePreferencia(dto.getNomePreferencia());
        }
        
        if (dto.getDataNascimento() != null) {
            usuario.setDataNascimento(dto.getDataNascimento());
        }
        
        if (dto.getTelefone() != null) {
            usuario.setTelefone(dto.getTelefone());
        }
        
        if (dto.getSexo() != null) {
            usuario.setSexo(dto.getSexo());
        }
        
        if (dto.getNacionalidade() != null) {
            usuario.setNacionalidade(dto.getNacionalidade());
        }
        
        if (dto.getCidade() != null) {
            usuario.setCidade(dto.getCidade());
        }
        
        if (dto.getEstado() != null) {
            usuario.setEstado(dto.getEstado());
        }
        
        if (dto.getEscolaridade() != null) {
            usuario.setEscolaridade(dto.getEscolaridade());
        }
        
        if (dto.getProfissao() != null) {
            usuario.setProfissao(dto.getProfissao());
        }
        
        if (dto.getFaixaRendaMensal() != null) {
            usuario.setFaixaRendaMensal(dto.getFaixaRendaMensal());
        }
        
        if (dto.getEmail() != null && !dto.getEmail().equals(usuario.getEmail())) {
            // Verificar se o novo email já está em uso
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                log.error("Email já em uso: {}", dto.getEmail());
                return Optional.empty();
            }
            usuario.setEmail(dto.getEmail());
        }
        
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        log.info("Usuário atualizado com sucesso: {}", usuarioAtualizado.getEmail());
        
        return Optional.of(usuarioAtualizado);
    }

    @Override
    public boolean remover(String id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }
        
        usuarioRepository.deleteById(id);
        log.info("Usuário removido: {}", id);
        return true;
    }
} 