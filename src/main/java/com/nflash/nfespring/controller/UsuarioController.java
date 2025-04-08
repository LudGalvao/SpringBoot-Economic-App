package com.nflash.nfespring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nflash.nfespring.entity.Usuario;
import com.nflash.nfespring.service.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PutMapping("/editarUsuario/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable int id, @RequestBody Usuario novoUsuario){
        Usuario atualizado = usuarioService.atualizarUsuario(id, novoUsuario);
        return  ResponseEntity.ok(atualizado);
    }
}
