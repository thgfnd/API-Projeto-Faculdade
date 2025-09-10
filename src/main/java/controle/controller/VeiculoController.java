package controle.controller;

import controle.model.Carro;
import controle.service.GerenciadorVeiculos;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final GerenciadorVeiculos gerenciador;

    public VeiculoController(GerenciadorVeiculos gerenciador) {
        this.gerenciador = gerenciador;
    }

    @GetMapping
    public List<Carro> listar() {
        return gerenciador.getVeiculos();
    }

    @GetMapping("/{id}")
    public Carro buscar(@PathVariable int id) {
        return gerenciador.buscarPorId(id);
    }

    @PostMapping
    public String adicionar(@RequestBody Carro carro) {
        Carro salvo = gerenciador.adicionarVeiculo(carro);
        return "Veículo registrado com sucesso! ID: " + salvo.getId();
    }

    @PutMapping("/{id}/quilometragem")
    public String atualizarKm(@PathVariable int id, @RequestParam int kmAtual) {
        Carro carro = gerenciador.buscarPorId(id);
        if (carro == null) return "Veículo não encontrado!";
        carro.setQuilometragem(kmAtual);
        gerenciador.adicionarVeiculo(carro); // Atualiza no banco
        return "Quilometragem atualizada!";
    }

    @GetMapping("/{id}/status-oleo")
    public String statusOleo(@PathVariable int id) {
        Carro carro = gerenciador.buscarPorId(id);
        if (carro == null) return "Veículo não encontrado!";
        return carro.getControleTrocaOleo().statusTroca(carro.getQuilometragem());
    }

    @PostMapping("/{id}/registrar-oleo")
    public String registrarOleo(@PathVariable int id) {
        Carro carro = gerenciador.buscarPorId(id);
        if (carro == null) return "Veículo não encontrado!";
        carro.getControleTrocaOleo().registrarTroca(carro.getQuilometragem());
        gerenciador.adicionarVeiculo(carro); // Salva atualização
        return "Troca de óleo registrada!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id) {
        gerenciador.deletar(id);
        return "Veículo removido!";
    }
}
