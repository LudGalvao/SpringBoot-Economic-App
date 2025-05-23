package com.economic.app.economic_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "cartoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {
    
    @Id
    private String id;
    
    private String usuarioId;
    private String nome;
    private String bandeira;
    private String banco;
    private Integer diaFechamento;
    private Integer diaVencimento;
    private Integer limite;
} 