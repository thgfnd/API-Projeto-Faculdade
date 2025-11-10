package com.thiago.controle.controller;

import com.thiago.controle.dto.CarroResponseDTO;
// import java.util.stream.Collectors; // <-- NÃO É MAIS NECESSÁRIO AQUI

import com.thiago.controle.model.Carro;
import com.thiago.controle.request.CarroRequest;
import com.thiago.controle.service.GerenciadorVeiculos;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final GerenciadorVeiculos gerenciador;

    public VeiculoController(GerenciadorVeiculos gerenciador) {
        this.gerenciador = gerenciador;
    }

    // --- MÉTODO MODIFICADO (SIMPLIFICADO) ---
    @GetMapping("/meus-veiculos")
    public List<CarroResponseDTO> listarVeiculosDoCliente(Principal principal) {
        // Apenas repassa a lista de DTOs do serviço
        return gerenciador.getVeiculosPorEmailUsuario(principal.getName());
    }

    @PostMapping("/meus-veiculos")
    public Carro adicionarMeuVeiculo(@RequestBody CarroRequest request, Principal principal) {
        return gerenciador.adicionarVeiculoParaCliente(request, principal.getName());
    }

    // --- MÉTODO MODIFICADO (SIMPLIFICADO) ---
    @GetMapping
    public List<CarroResponseDTO> listarTodos() {
        // Apenas repassa a lista de DTOs do serviço
        return gerenciador.getVeiculos();
    }

    // O restante dos métodos permanece igual
    @GetMapping("/{id}")
    public Carro buscar(@PathVariable int id) {
        return gerenciador.buscarPorId(id);
    }

    @PostMapping
    public String adicionar(@RequestBody CarroRequest request, @RequestParam Long usuarioId) {
        gerenciador.adicionarVeiculo(request, usuarioId);
        return "Veículo registrado com sucesso para o usuário ID: " + usuarioId;
    }

    @PutMapping("/{id}")
    public Carro atualizarVeiculo(@PathVariable int id, @RequestBody Carro carroAtualizado) {
        return gerenciador.atualizarVeiculo(id, carroAtualizado);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id) {
        gerenciador.deletar(id);
        return "Veículo removido!";
    }

    @GetMapping("/{id}/status-oleo")
    public String statusOleo(@PathVariable int id, Principal principal) {
        return gerenciador.getStatusOleo(id, principal.getName());
    }

    @PostMapping("/{id}/registrar-oleo")
    public String registrarOleo(@PathVariable int id, Principal principal) {
        gerenciador.registrarTrocaOleo(id, principal.getName());
        return "Troca de óleo registrada com sucesso!";
    }

    @PutMapping("/{id}/quilometragem")
    public String atualizarKm(@PathVariable int id, @RequestParam int kmAtual, Principal principal) {
        gerenciador.atualizarQuilometragem(id, kmAtual, principal.getName());
        return "Quilometragem atualizada com sucesso!";
    }

    @PutMapping("/{id}/intervalos-troca")
    public String atualizarIntervalos(@PathVariable int id, @RequestParam int intervaloKm, @RequestParam int intervaloMeses, Principal principal) {
        gerenciador.atualizarIntervalosTroca(id, intervaloKm, intervaloMeses, principal.getName());
        return "Intervalos de troca atualizados com sucesso!";
    }
}