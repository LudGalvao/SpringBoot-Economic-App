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
@Table(name = "cliente")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_cliente", nullable = false)
    private int id;

    @Column(columnDefinition = "VARCHAR", length = 255 , name = "nome_razao_social" , nullable = false)
    private String nomeSocial;

    @Column(columnDefinition = "VARCHAR", length = 14, name = "cpf_cnpj", nullable = false)
    private String cpfCnpj;

    @Column(columnDefinition = "VARCHAR", length = 20, name = "inscricao_estadual")
    private String inscricaoEstadual;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "email")
    private String email;

    @Column(columnDefinition = "VARCHAR", length = 20, name = "telefone")
    private String telefone;

    @Column(columnDefinition = "JSON", name = "endereco")
    private String endereco;

    @Column(columnDefinition = "TIMESTAMP", name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(columnDefinition = "TINYINT(1)", name = "ativo")
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
