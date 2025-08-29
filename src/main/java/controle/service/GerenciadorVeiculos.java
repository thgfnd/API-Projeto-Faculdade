package controle.service;

import controle.model.Carro;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorVeiculos {
    private final List<Carro> veiculos = new ArrayList<>();
    private int contadorId = 1;

    public void adicionarVeiculo(Carro carro) {
        carro = new Carro(contadorId++, carro.getModelo(), carro.getAno(), carro.getQuilometragem(), carro.getControleTrocaOleo());
        veiculos.add(carro);
    }

    public List<Carro> getVeiculos() {
        return veiculos;
    }

    public Carro buscarPorId(int id) {
        return veiculos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
}
