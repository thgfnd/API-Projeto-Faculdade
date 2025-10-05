package com.thiago.controle;

import com.thiago.controle.model.Usuario;
import com.thiago.controle.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            System.out.println("Criando usuários iniciais com dados completos...");

            // Criando usuário ADMIN com dados fictícios
            Usuario admin = new Usuario(
                    "Administrador",
                    "000.000.000-00",
                    "(00) 00000-0000",
                    "admin@email.com",
                    passwordEncoder.encode("admin"),
                    "ROLE_ADMIN"
            );
            usuarioRepository.save(admin);

            // Criando usuário CLIENTE com dados fictícios
            Usuario cliente = new Usuario(
                    "Cliente Teste",
                    "111.111.111-11",
                    "(11) 11111-1111",
                    "cliente@email.com",
                    passwordEncoder.encode("cliente"),
                    "ROLE_CLIENTE"
            );
            usuarioRepository.save(cliente);

            System.out.println("Usuários criados com sucesso!");
        }
    }
}