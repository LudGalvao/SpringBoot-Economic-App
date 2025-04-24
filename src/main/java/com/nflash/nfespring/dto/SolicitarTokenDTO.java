package com.nflash.nfespring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SolicitarTokenDTO {
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;
}
