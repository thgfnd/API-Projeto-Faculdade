package com.thiago.controle.controller;

import com.thiago.controle.model.Usuario;
import com.thiago.controle.request.UsuarioRequest;
import com.thiago.controle.service.GerenciadorVeiculos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final GerenciadorVeiculos gerenciador;

    public UsuarioController(GerenciadorVeiculos gerenciador) {
        this.gerenciador = gerenciador;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            gerenciador.registrarNovoUsuario(usuarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // ... (antes estava o código com .getVeiculosPorEmailUsuario) ...
    @GetMapping("/me")
    public Usuario getUsuarioLogado(Principal principal) {
        // Agora ele busca o usuário diretamente pelo e-mail (que é o principal.getName())
        return gerenciador.getUsuarioPorEmail(principal.getName());
    }
}