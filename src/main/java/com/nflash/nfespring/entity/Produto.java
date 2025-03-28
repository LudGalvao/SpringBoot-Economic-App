package com.nflash.nfespring.entity;

import java.math.BigDecimal;

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
@Table(name = "produto")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_produto", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(columnDefinition = "VARCHAR",  length = 50 , name = "codigo", nullable = false)
    private String codigo;

    @Column(columnDefinition = "VARCHAR", length = 120, name = "descricao", nullable = false)
    private String descricao;

    @Column(columnDefinition = "VARCHAR", length = 8, name = "ncm")
    private String ncm;

    @Column(columnDefinition = "VARCHAR", length =  7, name = "cest")
    private String cest;

    @Column(columnDefinition = "VARCHAR", length = 6, name = "unidade")
    private String unidade;

    @Column(precision = 15, scale = 2, name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(columnDefinition = "VARCHAR", length = 14, name = "codigo_ean")
    private String codigoEan;

    @Column(columnDefinition = "VARCHAR", length = 4, name = "codigo_cfop")
    private String codigoCfop;

    @Column(precision = 5, scale = 2, name = "aliquota_icms")
    private BigDecimal aliquotaIcms;

    @Column(precision = 5, scale = 2, name = "aliquota_pis")
    private BigDecimal aliquotaPis;

    @Column(precision = 5, scale = 2, name = "aliquota_confins")
    private BigDecimal aliquotaConfins;

    @Column(columnDefinition = "TINYINT(1)", name = "ativo")
    private boolean ativo;
}

