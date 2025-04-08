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
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
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
