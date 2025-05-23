package com.economic.app.economic_app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarCartaoDTO {

    @NotBlank(message = "O nome do cartão é obrigatório")
    private String nome;

    @NotBlank(message = "A bandeira do cartão é obrigatória")
    private String bandeira;

    @NotBlank(message = "O banco do cartão é obrigatório")
    private String banco;

    @NotNull(message = "O dia de fechamento do cartão é obrigatório")
    @Min(value = 1, message = "O dia de fechamento deve ser maior que 0")
    @Max(value = 31, message = "O dia de fechamento deve ser menor que 31")
    private Integer diaFechamento;

    @NotNull(message = "O dia de vencimento do cartão é obrigatório")
    @Min(value = 1, message = "O dia de vencimento deve ser maior que 0")
    @Max(value = 31, message = "O dia de vencimento deve ser menor que 31")
    private Integer diaVencimento;

    @NotNull(message = "O limite do cartão é obrigatório")
    @Min(value = 0, message = "O limite deve ser maior que 0")
    private Integer limite;
}
