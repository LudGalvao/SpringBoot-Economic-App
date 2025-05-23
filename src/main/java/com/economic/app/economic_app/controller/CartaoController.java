package com.economic.app.economic_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.app.economic_app.dto.CadastrarCartaoDTO;
import com.economic.app.economic_app.dto.CartaoResponseDTO;
import com.economic.app.economic_app.entity.Cartao;
import com.economic.app.economic_app.service.cartao.CartaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cartoes")
@RequiredArgsConstructor
@Tag(name = "Cartões", description = "Endpoints para gerenciamento de cartões de crédito")
@SecurityRequirement(name = "bearerAuth")
public class CartaoController {
    
    private final CartaoService cartaoService;
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cadastrar cartão", description = "Cadastra um novo cartão para o usuário logado")
    public ResponseEntity<CartaoResponseDTO> cadastrarCartao(@Valid @RequestBody CadastrarCartaoDTO dto) {
        Cartao cartao = cartaoService.cadastrarCartao(dto);
        return new ResponseEntity<>(CartaoResponseDTO.fromEntity(cartao), HttpStatus.CREATED);
    }
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar cartões", description = "Lista todos os cartões do usuário logado")
    public ResponseEntity<List<CartaoResponseDTO>> listarCartoes() {
        List<Cartao> cartoes = cartaoService.obterCartoesDoUsuario();
        
        List<CartaoResponseDTO> response = cartoes.stream()
                .map(CartaoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obter cartão por ID", description = "Obtém um cartão específico pelo ID")
    public ResponseEntity<CartaoResponseDTO> obterCartaoPorId(@PathVariable String id) {
        Cartao cartao = cartaoService.obterCartaoPorId(id);
        return ResponseEntity.ok(CartaoResponseDTO.fromEntity(cartao));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Excluir cartão", description = "Exclui um cartão específico pelo ID")
    public ResponseEntity<Void> excluirCartao(@PathVariable String id) {
        cartaoService.excluirCartao(id);
        return ResponseEntity.noContent().build();
    }
} 