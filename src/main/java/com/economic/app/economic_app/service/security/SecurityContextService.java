package com.economic.app.economic_app.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.economic.app.economic_app.entity.Usuario;
import com.economic.app.economic_app.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityContextService {
    
    private final UsuarioRepository usuarioRepository;
    
    /**
     * Obtém o ID do usuário autenticado a partir do contexto de segurança
     * 
     * @return ID do usuário autenticado
     * @throws RuntimeException se o usuário não estiver autenticado
     */
    public String getUsuarioIdAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }
        
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + email));
        
        return usuario.getId();
    }
    
    /**
     * Verifica se o usuário está autenticado
     * 
     * @return true se o usuário estiver autenticado, false caso contrário
     */
    public boolean isUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
    
    /**
     * Obtém o email do usuário autenticado
     * 
     * @return email do usuário autenticado
     * @throws RuntimeException se o usuário não estiver autenticado
     */
    public String getEmailUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }
        
        return authentication.getName();
    }
} 