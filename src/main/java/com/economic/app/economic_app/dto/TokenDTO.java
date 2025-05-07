package com.economic.app.economic_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    
    private String token;
    private String tipo;
    
    public TokenDTO(String token) {
        this.token = token;
        this.tipo = "Bearer";
    }
} 