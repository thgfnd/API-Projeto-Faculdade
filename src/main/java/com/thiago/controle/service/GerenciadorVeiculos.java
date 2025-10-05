package com.thiago.controle.service;

import com.thiago.controle.model.Carro;
import com.thiago.controle.model.ControleTrocaOleo;
import com.thiago.controle.model.Usuario;
import com.thiago.controle.repository.CarroRepository;
import com.thiago.controle.repository.UsuarioRepository;
import com.thiago.controle.request.CarroRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GerenciadorVeiculos {

    private final CarroRepository carroRepository;
    private final UsuarioRepository usuarioRepository;

    public GerenciadorVeiculos(CarroRepository carroRepository, UsuarioRepository usuarioRepository) {
        this.carroRepository = carroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Carro criarCarroAPartirDoRequest(CarroRequest request) {
        LocalDate dataTroca = LocalDate.parse(request.getDataUltimaTroca());
        ControleTrocaOleo controle = new ControleTrocaOleo(
                request.getKmUltimaTroca(),
                request.getIntervaloKm(),
                dataTroca,
                request.getIntervaloMeses()
        );
        Carro carro = new Carro();
        carro.setPlaca(request.getPlaca());
        carro.setModelo(request.getModelo());
        carro.setAno(request.getAno());
        carro.setQuilometragem(request.getQuilometragem());
        carro.setControleTrocaOleo(controle);
        return carro;
    }

    public Carro adicionarVeiculo(CarroRequest request, Long usuarioId) {
        Carro carro = criarCarroAPartirDoRequest(request);
        Usuario dono = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário dono não encontrado"));
        carro.setUsuario(dono);
        return carroRepository.save(carro);
    }

    public Carro adicionarVeiculoParaCliente(CarroRequest request, String emailUsuario) {
        Carro carro = criarCarroAPartirDoRequest(request);
        Usuario dono = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));
        carro.setUsuario(dono);
        return carroRepository.save(carro);
    }

    private Carro getCarroByIdAndVerifyOwner(int carroId, String userEmail) {
        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com ID: " + carroId));
        Usuario usuarioLogado = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));
        if (!carro.getUsuario().getId().equals(usuarioLogado.getId()) && !usuarioLogado.getPermissao().equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Você não tem permissão para acessar este veículo.");
        }
        return carro;
    }

    public String getStatusOleo(int carroId, String userEmail) {
        Carro carro = getCarroByIdAndVerifyOwner(carroId, userEmail);
        return carro.getControleTrocaOleo().statusTroca(carro.getQuilometragem());
    }

    public void registrarTrocaOleo(int carroId, String userEmail) {
        Carro carro = getCarroByIdAndVerifyOwner(carroId, userEmail);
        carro.getControleTrocaOleo().registrarTroca(carro.getQuilometragem());
        carroRepository.save(carro);
    }

    public void atualizarQuilometragem(int carroId, int kmAtual, String userEmail) {
        Carro carro = getCarroByIdAndVerifyOwner(carroId, userEmail);
        carro.setQuilometragem(kmAtual);
        carroRepository.save(carro);
    }

    public void atualizarIntervalosTroca(int carroId, int intervaloKm, int intervaloMeses, String userEmail) {
        Carro carro = getCarroByIdAndVerifyOwner(carroId, userEmail);
        ControleTrocaOleo controle = carro.getControleTrocaOleo();
        controle.setIntervaloKm(intervaloKm);
        controle.setIntervaloMeses(intervaloMeses);
        carroRepository.save(carro);
    }

    public Carro atualizarVeiculo(int id, Carro carroAtualizado) {
        Carro carroExistente = buscarPorId(id);
        if (carroExistente == null) {
            throw new RuntimeException("Veículo não encontrado para atualização com ID: " + id);
        }
        carroExistente.setPlaca(carroAtualizado.getPlaca());
        carroExistente.setModelo(carroAtualizado.getModelo());
        carroExistente.setAno(carroAtualizado.getAno());
        carroExistente.setQuilometragem(carroAtualizado.getQuilometragem());
        return carroRepository.save(carroExistente);
    }

    public List<Carro> getVeiculos() {
        return carroRepository.findAll();
    }

    public Carro buscarPorId(int id) {
        return carroRepository.findById(id).orElse(null);
    }

    public void deletar(int id) {
        carroRepository.deleteById(id);
    }

    public List<Carro> getVeiculosPorEmailUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));
        return carroRepository.findByUsuarioId(usuario.getId());
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }
}