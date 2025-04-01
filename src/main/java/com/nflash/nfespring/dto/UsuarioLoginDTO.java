package com.nflash.nfespring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String senha;
}
