package com.nflash.nfespring.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "plano")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_plano", nullable = false)
    private int id;

    @Column(columnDefinition = "VARCHAR", name = "nome", nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", name = "descricao")
    private String descricao;

    @Column(columnDefinition = "INT", name = "limite_nfe_mensal")
    private int limiteNfe;

    @Column(precision = 10, scale = 2, name = "valor")
    private BigDecimal valor;

    @Column(columnDefinition = "JSON", name = "recursos")
    private String recursos;
}
