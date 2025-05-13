package com.economic.app.economic_app.service.conta.impl;

import org.springframework.stereotype.Service;
import com.economic.app.economic_app.dto.ContaBancariaDTO;
import com.economic.app.economic_app.repository.ContaRepository;
import com.economic.app.economic_app.service.conta.ContaValidationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaValidationServiceImpl implements ContaValidationService {

    private final ContaRepository contaRepository;

    @Override
    public void validarConta(ContaBancariaDTO contaBancariaDTO) {
        if (contaBancariaDTO == null) {
            throw new IllegalArgumentException("Dados da conta não podem ser nulos");
        }
        
        if (contaBancariaDTO.getInstituicaoFinanceira() == null || contaBancariaDTO.getInstituicaoFinanceira().trim().isEmpty()) {
            throw new IllegalArgumentException("Instituição financeira é obrigatória");
        }
        
        if (contaBancariaDTO.getDescricao() == null || contaBancariaDTO.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        
        if (contaBancariaDTO.getTipo() == null || contaBancariaDTO.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de conta é obrigatório");
        }
    }

    @Override
    public void validarPropriedade(String contaId, String usuarioId) {
        if (contaId == null || contaId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da conta é obrigatório");
        }
        
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }
        
        contaRepository.findById(contaId)
            .filter(conta -> conta.getUsuarioId().equals(usuarioId))
            .orElseThrow(() -> new IllegalArgumentException("Conta não pertence ao usuário"));
    }

    @Override
    public void validarInstituicaoFinanceira(String instituicaoFinanceira) {
        if (instituicaoFinanceira == null || instituicaoFinanceira.trim().isEmpty()) {
            throw new IllegalArgumentException("Instituição financeira é obrigatória");
        }
        
        // Aqui você pode adicionar mais validações específicas para instituições financeiras
        // Por exemplo, verificar se é uma instituição válida em uma lista pré-definida
    }
} 