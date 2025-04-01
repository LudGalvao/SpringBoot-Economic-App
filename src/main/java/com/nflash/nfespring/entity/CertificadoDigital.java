package com.nflash.nfespring.entity;

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
@Table(name = "certificado_digital")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CertificadoDigital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_certificado", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "nome", nullable = false)
    private String nome;

    @Column(columnDefinition = "VARCHAR", length = 14, name = "cpf_cnpj", nullable = false)
    private String cpfCnpj;

    @Column(columnDefinition = "DATE", name = "data_validade", nullable = false)
    private LocalDateTime dataValidade;

    @Column(columnDefinition = "LONGBLOB", name = "arquivo")
    private byte[] arquivo;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "senha")
    private String senha;

    @Column(columnDefinition = "TINYINT(1)", name = "ativo")
    private boolean ativo;

    @Column(columnDefinition = "TIMESTAMP", name = "data_upload")
    private LocalDateTime dataUpload;
}
