package com.nflash.nfespring.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nflash.nfespring.dto.EnderecoDTO;

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
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_usuario", nullable = false)
    private int id;

    @Column(columnDefinition = "VARCHAR", length = 64, name = "username", nullable = false)
    private String nome;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "email", nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "senha_hash", nullable = false)
    private String senha;

    @Column(columnDefinition = "VARCHAR" , length = 14, name = "cpf", nullable = false)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "tipo_plano_id")
    private Plano plano;

    @Column(columnDefinition = "TIMESTAMP", name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(columnDefinition = "TIMESTAMP", name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(columnDefinition = "TINYINT(1)", name = "ativo")
    private boolean ativo;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "razao_social")
    private String razaoSocial;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "nome_fantasia")
    private String nomeFantasia;

    @Column(columnDefinition = "VARCHAR", length = 20, name = "telefone")
    private String telefone;

    @Column(columnDefinition = "JSON", name = "endereco")
    private String endereco;

    @Column(columnDefinition = "TIMESTAMP", name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;

    @Column(columnDefinition = "VARCHAR", length = 255, name = "reset_token")
    private String resetToken;

    public EnderecoDTO getEnderecoDTO() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(this.endereco, EnderecoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para EnderecoDTO", e);
        }
    }
}
