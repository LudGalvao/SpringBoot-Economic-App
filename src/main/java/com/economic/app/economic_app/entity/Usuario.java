package com.economic.app.economic_app.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    
    @Id
    private String id;
    
    private String nomeCompleto;
    private String nomePreferencia;
    private LocalDate dataNascimento;
    private String telefone;
    private String sexo;
    private String nacionalidade;
    private String cidade;
    private String estado;
    private String escolaridade;
    private String profissao;
    private String faixaRendaMensal;
    private String email;
    private String senha;
} 