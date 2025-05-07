package com.economic.app.economic_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedefinicaoSenhaDTO {
    
    @NotBlank(message = "O token é obrigatório")
    private String token;
    
    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String novaSenha;
    
    @NotBlank(message = "A confirmação de senha é obrigatória")
    private String confirmacaoSenha;
} 