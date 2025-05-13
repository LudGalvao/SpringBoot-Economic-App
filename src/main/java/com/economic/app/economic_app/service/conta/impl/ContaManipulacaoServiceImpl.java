package com.economic.app.economic_app.service.conta.impl;

import org.springframework.stereotype.Service;
import com.economic.app.economic_app.dto.ContaBancariaDTO;
import com.economic.app.economic_app.entity.Conta;
import com.economic.app.economic_app.repository.ContaRepository;
import com.economic.app.economic_app.service.conta.ContaConsultaService;
import com.economic.app.economic_app.service.conta.ContaManipulacaoService;
import com.economic.app.economic_app.service.conta.ContaValidationService;
import com.economic.app.economic_app.service.security.SecurityContextService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaManipulacaoServiceImpl implements ContaManipulacaoService {

    private final ContaRepository contaRepository;
    private final ContaValidationService validationService;
    private final SecurityContextService securityContextService;
    private final ContaConsultaService contaConsultaService;

    @Override
    public Conta cadastrarConta(ContaBancariaDTO contaBancariaDTO) {
        validationService.validarConta(contaBancariaDTO);
        
        String usuarioId = securityContextService.getUsuarioIdAutenticado();
        
        Conta conta = new Conta();
        conta.setUsuarioId(usuarioId);
        conta.setInstituicaoFinanceira(contaBancariaDTO.getInstituicaoFinanceira());
        conta.setDescricao(contaBancariaDTO.getDescricao());
        conta.setTipo(contaBancariaDTO.getTipo());
        conta.setCor(contaBancariaDTO.getCor());
        
        return contaRepository.save(conta);
    }

    @Override
    public Conta atualizarConta(String id, ContaBancariaDTO contaBancariaDTO) {
        validationService.validarConta(contaBancariaDTO);
        
        Conta contaExistente = contaConsultaService.buscarContaPorId(id);
        
        contaExistente.setInstituicaoFinanceira(contaBancariaDTO.getInstituicaoFinanceira());
        contaExistente.setDescricao(contaBancariaDTO.getDescricao());
        contaExistente.setTipo(contaBancariaDTO.getTipo());
        contaExistente.setCor(contaBancariaDTO.getCor());
        
        return contaRepository.save(contaExistente);
    }

    @Override
    public Conta atualizarContaPorDescricao(String descricao, ContaBancariaDTO contaBancariaDTO) {
        validationService.validarConta(contaBancariaDTO);
        
        Conta contaExistente = contaConsultaService.buscarContaPorDescricao(descricao);
        
        contaExistente.setInstituicaoFinanceira(contaBancariaDTO.getInstituicaoFinanceira());
        contaExistente.setDescricao(contaBancariaDTO.getDescricao());
        contaExistente.setTipo(contaBancariaDTO.getTipo());
        contaExistente.setCor(contaBancariaDTO.getCor());
        
        return contaRepository.save(contaExistente);
    }

    @Override
    public void removerConta(String id) {
        Conta conta = contaConsultaService.buscarContaPorId(id);
        contaRepository.delete(conta);
    }

    @Override
    public void removerContaPorDescricao(String descricao) {
        Conta conta = contaConsultaService.buscarContaPorDescricao(descricao);
        contaRepository.delete(conta);
    }
} 