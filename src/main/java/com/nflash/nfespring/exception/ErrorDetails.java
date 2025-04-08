package com.nflash.nfespring.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private LocalDateTime registroHora;
    private String mensagemError;
    private String detalhesError;

    public ErrorDetails(LocalDateTime registroHora, String mensagemError, String detalhesError){
        super();
        this.registroHora = registroHora;
        this.mensagemError = mensagemError;
        this.detalhesError = detalhesError;
    }
}
