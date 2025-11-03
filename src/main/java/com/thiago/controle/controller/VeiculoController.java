package com.thiago.controle.controller;

import com.thiago.controle.model.Carro;
import com.thiago.controle.request.CarroRequest;
import com.thiago.controle.service.GerenciadorVeiculos;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos") // <-- Base da URL para todos os endpoints
public class VeiculoController {

    private final GerenciadorVeiculos gerenciador;

    public VeiculoController(GerenciadorVeiculos gerenciador) {
        this.gerenciador = gerenciador;
    }

    // ANTES: @GetMapping("/veiculos/meus-veiculos")
    // CORRETO:
    @GetMapping("/meus-veiculos")
    public List<Carro> listarVeiculosDoCliente(Principal principal) {
        return gerenciador.getVeiculosPorEmailUsuario(principal.getName());
    }

    // ANTES: @PostMapping("/veiculos/meus-veiculos")
    // CORRETO:
    @PostMapping("/meus-veiculos")
    public Carro adicionarMeuVeiculo(@RequestBody CarroRequest request, Principal principal) {
        return gerenciador.adicionarVeiculoParaCliente(request, principal.getName());
    }

    // ANTES: @GetMapping("/veiculos")
    // CORRETO: (Endpoint para /api/veiculos)
    @GetMapping
    public List<Carro> listarTodos() {
        return gerenciador.getVeiculos();
    }

    // ANTES: @GetMapping("/veiculos/{id}")
    // CORRETO:
    @GetMapping("/{id}")
    public Carro buscar(@PathVariable int id) {
        return gerenciador.buscarPorId(id);
    }

    // ANTES: @PostMapping("/veiculos")
    // CORRETO: (Endpoint para /api/veiculos)
    @PostMapping
    public String adicionar(@RequestBody CarroRequest request, @RequestParam Long usuarioId) {
        gerenciador.adicionarVeiculo(request, usuarioId);
        return "Veículo registrado com sucesso para o usuário ID: " + usuarioId;
    }

    // ANTES: @PutMapping("/veiculos/{id}")
    // CORRETO:
    @PutMapping("/{id}")
    public Carro atualizarVeiculo(@PathVariable int id, @RequestBody Carro carroAtualizado) {
        return gerenciador.atualizarVeiculo(id, carroAtualizado);
    }

    // ANTES: @DeleteMapping("/veiculos/{id}")
    // CORRETO:
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id) {
        gerenciador.deletar(id);
        return "Veículo removido!";
    }

    // ANTES: @GetMapping("/veiculos/{id}/status-oleo")
    // CORRETO:
    @GetMapping("/{id}/status-oleo")
    public String statusOleo(@PathVariable int id, Principal principal) {
        return gerenciador.getStatusOleo(id, principal.getName());
    }

    // ANTES: @PostMapping("/veiculos/{id}/registrar-oleo")
    // CORRETO:
    @PostMapping("/{id}/registrar-oleo")
    public String registrarOleo(@PathVariable int id, Principal principal) {
        gerenciador.registrarTrocaOleo(id, principal.getName());
        return "Troca de óleo registrada com sucesso!";
    }

    // ANTES: @PutMapping("/veiculos/{id}/quilometragem")
    // CORRETO:
    @PutMapping("/{id}/quilometragem")
    public String atualizarKm(@PathVariable int id, @RequestParam int kmAtual, Principal principal) {
        gerenciador.atualizarQuilometragem(id, kmAtual, principal.getName());
        return "Quilometragem atualizada com sucesso!";
    }

    // ANTES: @PutMapping("/veiculos/{id}/intervalos-troca")
    // CORRETO:
    @PutMapping("/{id}/intervalos-troca")
    public String atualizarIntervalos(@PathVariable int id, @RequestParam int intervaloKm, @RequestParam int intervaloMeses, Principal principal) {
        gerenciador.atualizarIntervalosTroca(id, intervaloKm, intervaloMeses, principal.getName());
        return "Intervalos de troca atualizados com sucesso!";
    }
}