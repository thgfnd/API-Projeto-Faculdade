package com.thiago.controle.controller;

import com.thiago.controle.model.Carro;
import com.thiago.controle.model.Usuario;
import com.thiago.controle.request.CarroRequest;
import com.thiago.controle.service.GerenciadorVeiculos;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VeiculoController {

    private final GerenciadorVeiculos gerenciador;

    public VeiculoController(GerenciadorVeiculos gerenciador) {
        this.gerenciador = gerenciador;
    }

    @GetMapping("/veiculos/meus-veiculos")
    public List<Carro> listarVeiculosDoCliente(Principal principal) {
        return gerenciador.getVeiculosPorEmailUsuario(principal.getName());
    }

    @PostMapping("/veiculos/meus-veiculos")
    public Carro adicionarMeuVeiculo(@RequestBody CarroRequest request, Principal principal) {
        return gerenciador.adicionarVeiculoParaCliente(request, principal.getName());
    }

    @GetMapping("/veiculos")
    public List<Carro> listarTodos() {
        return gerenciador.getVeiculos();
    }

    @GetMapping("/veiculos/{id}")
    public Carro buscar(@PathVariable int id) {
        return gerenciador.buscarPorId(id);
    }

    @PostMapping("/veiculos")
    public String adicionar(@RequestBody CarroRequest request, @RequestParam Long usuarioId) {
        Carro salvo = gerenciador.adicionarVeiculo(request, usuarioId);
        return "Veículo registrado com sucesso para o usuário ID: " + usuarioId;
    }

    @PutMapping("/veiculos/{id}")
    public Carro atualizarVeiculo(@PathVariable int id, @RequestBody Carro carroAtualizado) {
        return gerenciador.atualizarVeiculo(id, carroAtualizado);
    }

    @DeleteMapping("/veiculos/{id}")
    public String deletar(@PathVariable int id) {
        gerenciador.deletar(id);
        return "Veículo removido!";
    }

    @GetMapping("/veiculos/{id}/status-oleo")
    public String statusOleo(@PathVariable int id, Principal principal) {
        return gerenciador.getStatusOleo(id, principal.getName());
    }

    @PostMapping("/veiculos/{id}/registrar-oleo")
    public String registrarOleo(@PathVariable int id, Principal principal) {
        gerenciador.registrarTrocaOleo(id, principal.getName());
        return "Troca de óleo registrada com sucesso!";
    }

    @PutMapping("/veiculos/{id}/quilometragem")
    public String atualizarKm(@PathVariable int id, @RequestParam int kmAtual, Principal principal) {
        gerenciador.atualizarQuilometragem(id, kmAtual, principal.getName());
        return "Quilometragem atualizada com sucesso!";
    }

    @PutMapping("/veiculos/{id}/intervalos-troca")
    public String atualizarIntervalos(@PathVariable int id, @RequestParam int intervaloKm, @RequestParam int intervaloMeses, Principal principal) {
        gerenciador.atualizarIntervalosTroca(id, intervaloKm, intervaloMeses, principal.getName());
        return "Intervalos de troca atualizados com sucesso!";
    }

    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return gerenciador.getUsuarios();
    }
}