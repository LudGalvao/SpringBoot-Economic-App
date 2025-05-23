package com.economic.app.economic_app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.economic.app.economic_app.entity.Cartao;

@Repository
@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
public interface CartaoRepository extends MongoRepository<Cartao, String> {
    
    List<Cartao> findByUsuarioId(String usuarioId);
    
    boolean existsByNomeAndUsuarioId(String nome, String usuarioId);
} 