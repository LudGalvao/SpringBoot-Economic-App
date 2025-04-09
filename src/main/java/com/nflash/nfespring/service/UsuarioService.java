package com.nflash.nfespring.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nflash.nfespring.entity.Usuario;
import com.nflash.nfespring.exception.ResourceNotFoundException;
import com.nflash.nfespring.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(int id, Usuario novoUsuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        
        // Verifica se o CPF foi alterado
        if (!usuarioExistente.getCpf().equals(novoUsuario.getCpf())) {
            // Verifica se o novo CPF já existe para outro usuário
            usuarioRepository.findByCpf(novoUsuario.getCpf()).ifPresent(u -> {
                if (u.getId() != id) {
                    throw new IllegalArgumentException("CPF já cadastrado para outro usuário: " + novoUsuario.getCpf());
                }
            });
        }
        
        // Verifica se o e-mail foi alterado
        if (!usuarioExistente.getEmail().equals(novoUsuario.getEmail())) {
            // Verifica se o novo e-mail já existe para outro usuário
            usuarioRepository.findByEmail(novoUsuario.getEmail()).ifPresent(u -> {
                if (u.getId() != id) {
                    throw new IllegalArgumentException("E-mail já cadastrado para outro usuário: " + novoUsuario.getEmail());
                }
            });
        }
    
        usuarioExistente.setNome(novoUsuario.getNome());
        usuarioExistente.setEmail(novoUsuario.getEmail());
    
        // Codifica a nova senha, se fornecida
        if (novoUsuario.getSenha() != null && !novoUsuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));
        }
    
        usuarioExistente.setCpf(novoUsuario.getCpf());
        usuarioExistente.setPlano(novoUsuario.getPlano());
        usuarioExistente.setUltimoLogin(novoUsuario.getUltimoLogin());
        usuarioExistente.setAtivo(novoUsuario.isAtivo());
        usuarioExistente.setRazaoSocial(novoUsuario.getRazaoSocial());
        usuarioExistente.setNomeFantasia(novoUsuario.getNomeFantasia());
        usuarioExistente.setTelefone(novoUsuario.getTelefone());
        usuarioExistente.setEndereco(novoUsuario.getEndereco());

    
        return usuarioRepository.save(usuarioExistente);
    }
    
    public void excluirUsuario(int id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        
        // Opção 2: Exclusão lógica (alternativa)
         usuario.setAtivo(false);
         usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email){                  
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getSenha())
            .roles("USER")
            .build();
    }
}
