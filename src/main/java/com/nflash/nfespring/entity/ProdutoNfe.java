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
@Table(name = "nfe")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoNfe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_produto_nfe", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_nfe")
    private Nfe nfe;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @Column(precision = 10, scale = 3, name = "quantidade", nullable = false)
    private BigDecimal quantidade;

    @Column(precision = 15, scale = 2, name = "valor_unitario", nullable = false)
    private BigDecimal valorUnitario;

    @Column(precision = 15, scale = 2, name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @Column(precision = 5, scale = 2, name = "aliquota_icms", nullable = false)
    private BigDecimal aliquotaIcms;

    @Column(precision = 5, scale = 2, name = "aliquota_pis", nullable = false)
    private BigDecimal aliquotaPis;

    @Column(precision = 5, scale = 2, name = "aliquota_cofins", nullable = false)
    private BigDecimal aliquotaCofins;

}
