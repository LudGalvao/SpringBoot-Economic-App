package com.economic.app.economic_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancariaDTO {

    @NotBlank(message = "A instituição financeira é obrigatória")
    private String instituicaoFinanceira;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "O tipo é obrigatório")
    private String tipo;

    // Campo opcional, sem validação especifica
    private String cor;
}
