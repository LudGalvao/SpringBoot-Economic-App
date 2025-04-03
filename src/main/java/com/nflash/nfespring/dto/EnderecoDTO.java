package com.nflash.nfespring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    
    @NotBlank
    private String uf;

    @NotBlank
    private String cep;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String numero;

    @NotBlank
    private String logradouro;

    private String complemento;
}
