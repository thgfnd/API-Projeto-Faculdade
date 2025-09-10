package controle.service;

import controle.model.Carro;
import controle.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Indica que é uma camada de serviço
public class GerenciadorVeiculos {

    private final CarroRepository carroRepository;

    public GerenciadorVeiculos(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public Carro adicionarVeiculo(Carro carro) {
        return carroRepository.save(carro); // Salva no banco
    }

    public List<Carro> getVeiculos() {
        return carroRepository.findAll(); // Retorna todos
    }

    public Carro buscarPorId(int id) {
        return carroRepository.findById(id).orElse(null);
    }

    public void deletar(int id) {
        carroRepository.deleteById(id);
    }
}
