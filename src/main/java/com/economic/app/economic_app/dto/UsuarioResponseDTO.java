package com.economic.app.economic_app.dto;

import java.time.LocalDate;

import com.economic.app.economic_app.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    
    private String id;
    private String nomeCompleto;
    private String nomePreferencia;
    private LocalDate dataNascimento;
    private String telefone;
    private String sexo;
    private String nacionalidade;
    private String cidade;
    private String estado;
    private String escolaridade;
    private String profissao;
    private String faixaRendaMensal;
    private String email;
    
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNomeCompleto(usuario.getNomeCompleto());
        dto.setNomePreferencia(usuario.getNomePreferencia());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setTelefone(usuario.getTelefone());
        dto.setSexo(usuario.getSexo());
        dto.setNacionalidade(usuario.getNacionalidade());
        dto.setCidade(usuario.getCidade());
        dto.setEstado(usuario.getEstado());
        dto.setEscolaridade(usuario.getEscolaridade());
        dto.setProfissao(usuario.getProfissao());
        dto.setFaixaRendaMensal(usuario.getFaixaRendaMensal());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
} 