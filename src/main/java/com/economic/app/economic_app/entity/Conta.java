package com.economic.app.economic_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contas")
public class Conta {
    
    @Id
    private String id;
    
    private String usuarioId;
    private String instituicaoFinanceira;
    private String descricao;
    private String tipo;
    private String cor;
} 