package com.economic.app.economic_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.economic.app.economic_app.dto.ContaBancariaDTO;
import com.economic.app.economic_app.entity.Conta;
import com.economic.app.economic_app.service.conta.ContaConsultaService;
import com.economic.app.economic_app.service.conta.ContaManipulacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contas")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Contas", description = "API para gerenciamento de contas bancárias")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ContaController {
    
    private final ContaConsultaService contaConsultaService;
    private final ContaManipulacaoService contaManipulacaoService;
    
    @PostMapping
    @Operation(summary = "Cadastrar uma nova conta bancária", 
               description = "Endpoint para cadastro de conta bancária para o usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conta cadastrada com sucesso",
                     content = @Content(schema = @Schema(implementation = Conta.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Conta> cadastrarConta(@Valid @RequestBody ContaBancariaDTO contaBancariaDTO) {
        Conta novaConta = contaManipulacaoService.cadastrarConta(contaBancariaDTO);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }
    
    @GetMapping
    @Operation(summary = "Listar contas do usuário autenticado", 
               description = "Endpoint para listar todas as contas do usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contas retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<Conta>> listarContasDoUsuario() {
        List<Conta> contas = contaConsultaService.buscarContasPorUsuarioAutenticado();
        return ResponseEntity.ok(contas);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID", 
               description = "Endpoint para buscar conta por ID (apenas se pertencer ao usuário autenticado)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta encontrada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable String id) {
        Conta conta = contaConsultaService.buscarContaPorId(id);
        return ResponseEntity.ok(conta);
    }
    
    @GetMapping("/instituicao")
    @Operation(summary = "Buscar contas contendo parte do nome da instituição financeira", 
               description = "Endpoint para buscar contas que contêm parte do nome da instituição financeira (apenas se pertencerem ao usuário autenticado)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contas encontradas com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Nenhuma conta encontrada")
    })
    public ResponseEntity<List<Conta>> buscarContasPorInstituicaoFinanceiraContendo(@RequestParam String termo) {
        List<Conta> contas = contaConsultaService.buscarContasPorInstituicaoFinanceiraContendo(termo);
        return ResponseEntity.ok(contas);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta por ID", 
               description = "Endpoint para atualizar conta por ID (apenas se pertencer ao usuário autenticado)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<Conta> atualizarConta(@PathVariable String id, 
                                               @Valid @RequestBody ContaBancariaDTO contaBancariaDTO) {
        Conta contaAtualizada = contaManipulacaoService.atualizarConta(id, contaBancariaDTO);
        return ResponseEntity.ok(contaAtualizada);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover conta por ID", 
               description = "Endpoint para remover conta por ID (apenas se pertencer ao usuário autenticado)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Conta removida com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<Void> removerConta(@PathVariable String id) {
        contaManipulacaoService.removerConta(id);
        return ResponseEntity.noContent().build();
    }
}
