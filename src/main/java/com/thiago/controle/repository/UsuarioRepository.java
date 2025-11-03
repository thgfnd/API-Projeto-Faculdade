package com.thiago.controle.repository;

import com.thiago.controle.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo email
    Optional<Usuario> findByEmail(String email);

    // método para buscar por cpf

    Optional<Usuario> findByCpf(String cpf);
}