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
@Table(name = "log_eventos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogEventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", name = "id_log",  nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(columnDefinition = "VARCHAR", length = 50, name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(columnDefinition = "TEXT", name = "descricao", nullable = false)
    private String descricao;

    @Column(columnDefinition = "TIMESTAMP", name = "data_evento")
    private LocalDateTime dataEvento;

    @Column(columnDefinition = "VARCHAR", length = 45, name = "ip")
    private String ip;

    @Column(columnDefinition = "JSON", name = "dados_adicionais")
    private String dados;

}
