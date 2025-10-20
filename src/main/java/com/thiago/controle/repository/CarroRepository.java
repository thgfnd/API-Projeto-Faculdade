package com.thiago.controle.repository;

import com.thiago.controle.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer> {

    //
    List<Carro> findByUsuarioId(Long usuarioId);

    //
    Optional<Carro> findByPlaca(String placa);
}