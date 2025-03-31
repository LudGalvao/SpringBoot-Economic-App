package com.nflash.nfespring.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "nfe")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Nfe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_nfe", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(columnDefinition = "VARCHAR", length = 44, name = "chave_acesso")
    private String chave;

    @Column(columnDefinition = "INT", name = "numero",  nullable = false)
    private int numero;

    @Column(columnDefinition = "INT", name = "serie",  nullable = false)
    private int serie;

    @Column(columnDefinition = "VARCHAR", length = 2, name = "modelo")
    private String modelo;

    @Column(columnDefinition = "TIMESTAMP", name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(columnDefinition = "TIMESTAMP", name = "data_autorizacao")
    private LocalDateTime dataAutorizacao;

    @Column(columnDefinition = "VARCHAR", length = 20, name = "status", nullable = false)
    private String status;

    @Column(precision = 15, scale = 2, name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @Column(columnDefinition = "LONGTEXT", name = "xml")
    private String xml;

    @Column(columnDefinition = "JSON", name = "json_dados")
    private String jsonDados;

    @Column(columnDefinition = "JSON", name = "destinatario")
    private String destinatario;

    @Column(columnDefinition = "JSON", name = "emitente")
    private String emitente;

    @Column(columnDefinition = "TEXT", name = "informacoes_adicionais")
    private String informacoesAdicionais;

    @Column(columnDefinition = "TEXT", name = "motivo_cancelamento")
    private String motivoCancelamento;

}
