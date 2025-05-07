package com.economic.app.economic_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    
    @NotBlank(message = "O email é obrigatório")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória")
    private String senha;
} 