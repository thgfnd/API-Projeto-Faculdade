package com.thiago.controle.dto;

import com.thiago.controle.model.Carro;

public class CarroResponseDTO {
    private int id;
    private String placa;
    private String modelo;
    private int ano;
    private UsuarioResponseDTO usuario;
    private String statusOleo;

    public CarroResponseDTO(Carro carro) {
        this.id = carro.getId();
        this.placa = carro.getPlaca();
        this.modelo = carro.getModelo();
        this.ano = carro.getAno();
        this.usuario = new UsuarioResponseDTO(carro.getUsuario());

        // --- ESTA É A LINHA QUE ESTÁ FALTANDO NO SEU PROJETO ---
        // Ela chama o método statusOleo() do Carro.java
        this.statusOleo = carro.statusOleo();
        // --- FIM DA LINHA IMPORTANTE ---
    }

    // Getters
    public int getId() { return id; }
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public UsuarioResponseDTO getUsuario() { return usuario; }
    public String getStatusOleo() { return statusOleo; }
}