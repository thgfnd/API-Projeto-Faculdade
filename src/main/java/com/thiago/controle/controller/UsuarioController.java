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

    // Endpoint que já existia no VeiculoController, movido para cá para melhor organização
    @GetMapping("/me")
    public Usuario getUsuarioLogado(Principal principal) {
        // Supondo que você tenha um método no service para buscar por email
        return gerenciador.getVeiculosPorEmailUsuario(principal.getName()).get(0).getUsuario();
    }
}