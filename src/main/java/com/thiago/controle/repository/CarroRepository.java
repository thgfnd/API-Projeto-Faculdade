package com.thiago.controle.repository;

import com.thiago.controle.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Adicione este import

@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer> {

    // NOVO MÉTODO ADICIONADO ABAIXO
    List<Carro> findByUsuarioId(Long usuarioId);
}