package com.thiago.controle.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carros")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // RELACIONAMENTO COM USUÁRIO ADICIONADO ABAIXO
    @ManyToOne(fetch = FetchType.LAZY) // Muitos carros podem pertencer a Um usuário
    @JoinColumn(name = "usuario_id", nullable = false) // Chave estrangeira no banco
    private Usuario usuario;
    // ---------------------------------------------------

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    private int ano;
    private int quilometragem;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "controle_id")
    private ControleTrocaOleo controleTrocaOleo;

    public Carro() {}

    // Construtor atualizado para incluir o usuário
    public Carro(Usuario usuario, String placa, String modelo, int ano, int quilometragem, ControleTrocaOleo controleTrocaOleo) {
        this.usuario = usuario;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.controleTrocaOleo = controleTrocaOleo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; } // Getter para o usuário
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public int getQuilometragem() { return quilometragem; }
    public ControleTrocaOleo getControleTrocaOleo() { return controleTrocaOleo; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; } // Setter para o usuário
    public void setPlaca(String placa) { this.placa = placa; }
    // ... resto dos setters e métodos utilitários
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAno(int ano) { this.ano = ano; }
    public void setQuilometragem(int quilometragem) { this.quilometragem = quilometragem; }
    public void setControleTrocaOleo(ControleTrocaOleo controleTrocaOleo) { this.controleTrocaOleo = controleTrocaOleo; }

    public String statusOleo() {
        if (controleTrocaOleo == null) return "Controle de óleo não configurado!";
        return controleTrocaOleo.statusTroca(quilometragem);
    }

    public void registrarTrocaOleo() {
        if (controleTrocaOleo != null) {
            controleTrocaOleo.registrarTroca(quilometragem);
        }
    }
}