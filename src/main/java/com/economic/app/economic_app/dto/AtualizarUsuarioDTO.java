package com.economic.app.economic_app.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarUsuarioDTO {
    
    private String nomeCompleto;
    private String nomePreferencia;
    
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;
    
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Formato de telefone inválido")
    private String telefone;
    
    private String sexo;
    private String nacionalidade;
    private String cidade;
    private String estado;
    private String escolaridade;
    private String profissao;
    private String faixaRendaMensal;
    
    @Email(message = "Email inválido")
    private String email;
} 