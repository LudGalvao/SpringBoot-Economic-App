package com.economic.app.economic_app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.economic.app.economic_app.entity.Conta;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends MongoRepository<Conta, String> {
    List<Conta> findByUsuarioId(String usuarioId);
    
    Optional<Conta> findByDescricaoAndUsuarioId(String descricao, String usuarioId);
    
    Optional<Conta> findByInstituicaoFinanceiraAndUsuarioId(String instituicaoFinanceira, String usuarioId);
    
    List<Conta> findByInstituicaoFinanceiraContainingAndUsuarioId(String instituicaoFinanceira, String usuarioId);
}
