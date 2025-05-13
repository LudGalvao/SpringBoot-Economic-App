package com.economic.app.economic_app.service.conta.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.economic.app.economic_app.entity.Conta;
import com.economic.app.economic_app.repository.ContaRepository;
import com.economic.app.economic_app.exception.RecursoNaoEncontradoException;
import com.economic.app.economic_app.service.conta.ContaConsultaService;
import com.economic.app.economic_app.service.conta.ContaValidationService;
import com.economic.app.economic_app.service.security.SecurityContextService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaConsultaServiceImpl implements ContaConsultaService {

    private final ContaRepository contaRepository;
    private final ContaValidationService validationService;
    private final SecurityContextService securityContextService;

    @Override
    public List<Conta> buscarContasPorUsuarioAutenticado() {
        String usuarioId = securityContextService.getUsuarioIdAutenticado();
        return contaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Conta buscarContaPorId(String id) {
        String usuarioId = securityContextService.getUsuarioIdAutenticado();
        
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada com o ID: " + id));
        
        validationService.validarPropriedade(conta.getId(), usuarioId);
        
        return conta;
    }

    @Override
    public Conta buscarContaPorDescricao(String descricao) {
        String usuarioId = securityContextService.getUsuarioIdAutenticado();
        
        return contaRepository.findByDescricaoAndUsuarioId(descricao, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada com a descrição: " + descricao));
    }

    @Override
    public Conta buscarContaPorInstituicaoFinanceira(String instituicaoFinanceira) {
        String usuarioId = securityContextService.getUsuarioIdAutenticado();
        
        validationService.validarInstituicaoFinanceira(instituicaoFinanceira);
        
        return contaRepository.findByInstituicaoFinanceiraAndUsuarioId(instituicaoFinanceira, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                    "Conta não encontrada com a instituição financeira: " + instituicaoFinanceira));
    }

    @Override
    public List<Conta> buscarContasPorInstituicaoFinanceiraContendo(String instituicaoFinanceira) {
        String usuarioId = securityContextService.getUsuarioIdAutenticado();
        
        validationService.validarInstituicaoFinanceira(instituicaoFinanceira);
        
        List<Conta> contas = contaRepository.findByInstituicaoFinanceiraContainingAndUsuarioId(
            instituicaoFinanceira, usuarioId);
        
        if (contas.isEmpty()) {
            throw new RecursoNaoEncontradoException(
                "Nenhuma conta encontrada contendo a instituição financeira: " + instituicaoFinanceira);
        }
        
        return contas;
    }
} 