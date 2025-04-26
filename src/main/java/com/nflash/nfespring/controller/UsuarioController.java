package com.nflash.nfespring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nflash.nfespring.entity.Usuario;
import com.nflash.nfespring.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um usuário", description = "Atualiza as informações de um usuário existente")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable int id, @RequestBody Usuario novoUsuario) {
        Usuario atualizado = usuarioService.atualizarUsuario(id, novoUsuario);
        return ResponseEntity.ok(atualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um usuário", description = "Remove um usuário do sistema")
    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Void> excluirUsuario(@PathVariable int id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
