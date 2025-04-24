package com.nflash.nfespring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class novaSenhaDTO {
    @NotBlank(message = "O token é obrigatório")
    private String token;

    @NotBlank(message = "Nova senha é obrigatoria")
    private String novaSenha;
}
