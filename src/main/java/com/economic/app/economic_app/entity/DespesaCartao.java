package com.economic.app.economic_app.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "despesas_cartao")
public class DespesaCartao {
    
    @Id
    private String id;
    
    private String usuarioId;
    private Double valor;
    private LocalDate data;
    private String descricao;
    private String categoria;
    private String cartaoId;
    private LocalDate fechamentoFatura;
    private Boolean parcelado;
    private List<String> tags;
} 