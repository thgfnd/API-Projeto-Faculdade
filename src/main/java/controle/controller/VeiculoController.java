package controle.controller;

import controle.model.Carro;
import controle.model.ControleTrocaOleo;
import controle.service.GerenciadorVeiculos;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final GerenciadorVeiculos gerenciador = new GerenciadorVeiculos();

    @GetMapping
    public List<Carro> listar() {
        return gerenciador.getVeiculos();
    }

    @GetMapping("/{id}")
    public Carro buscar(@PathVariable int id) {
        Carro carro = gerenciador.buscarPorId(id);
        if (carro == null) throw new RuntimeException("Veículo não encontrado!");
        return carro;
    }

    @PostMapping
    public String adicionar(@RequestBody Carro carro) {
        gerenciador.adicionarVeiculo(carro);
        return "Veículo registrado com sucesso! ID: " + carro.getId();
    }

    @PutMapping("/{id}/quilometragem")
    public String atualizarKm(@PathVariable int id, @RequestParam int kmAtual) {
        Carro carro = gerenciador.buscarPorId(id);
        if (carro == null) return "Veículo não encontrado!";
        carro.setQuilometragem(kmAtual);
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
        return "Troca de óleo registrada!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id) {
        Carro carro = gerenciador.buscarPorId(id);
        if (carro == null) return "Veículo não encontrado!";
        gerenciador.getVeiculos().remove(carro);
        return "Veículo removido!";
    }

}
