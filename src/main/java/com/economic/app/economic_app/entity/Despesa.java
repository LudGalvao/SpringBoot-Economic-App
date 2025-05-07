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
@Document(collection = "despesas")
public class Despesa {
    
    @Id
    private String id;
    
    private String usuarioId;
    private Double valor;
    private Boolean pago;
    private LocalDate data;
    private String descricao;
    private String categoria;
    private String contaId;
    private List<String> tags;
} 