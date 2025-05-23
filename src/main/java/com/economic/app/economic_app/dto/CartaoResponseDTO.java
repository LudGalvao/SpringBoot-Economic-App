package com.economic.app.economic_app.dto;

import com.economic.app.economic_app.entity.Cartao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoResponseDTO {
    
    private String id;
    private String nome;
    private String bandeira;
    private String banco;
    private Integer diaFechamento;
    private Integer diaVencimento;
    private Integer limite;
    
    public static CartaoResponseDTO fromEntity(Cartao cartao) {
        CartaoResponseDTO dto = new CartaoResponseDTO();
        dto.setId(cartao.getId());
        dto.setNome(cartao.getNome());
        dto.setBandeira(cartao.getBandeira());
        dto.setBanco(cartao.getBanco());
        dto.setDiaFechamento(cartao.getDiaFechamento());
        dto.setDiaVencimento(cartao.getDiaVencimento());
        dto.setLimite(cartao.getLimite());
        return dto;
    }
} 