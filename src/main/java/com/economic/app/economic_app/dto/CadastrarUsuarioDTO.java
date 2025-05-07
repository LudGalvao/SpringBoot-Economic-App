package com.economic.app.economic_app.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarUsuarioDTO {
    
    @NotBlank(message = "O nome completo é obrigatório")
    private String nomeCompleto;
    
    @NotBlank(message = "O nome de preferência é obrigatório")
    private String nomePreferencia;
    
    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;
    
    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Formato de telefone inválido")
    private String telefone;
    
    @NotBlank(message = "O sexo é obrigatório")
    private String sexo;
    
    @NotBlank(message = "A nacionalidade é obrigatória")
    private String nacionalidade;
    
    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;
    
    @NotBlank(message = "O estado é obrigatório")
    private String estado;
    
    @NotBlank(message = "A escolaridade é obrigatória")
    private String escolaridade;
    
    @NotBlank(message = "A profissão é obrigatória")
    private String profissao;
    
    @NotBlank(message = "A faixa de renda mensal é obrigatória")
    private String faixaRendaMensal;
    
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;
    
    @NotBlank(message = "A confirmação de senha é obrigatória")
    private String confirmacaoSenha;
} 