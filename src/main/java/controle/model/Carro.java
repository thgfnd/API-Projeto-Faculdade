package controle.model;

import jakarta.persistence.*; // IMPORT CORRETO PARA SPRING BOOT 3 / JPA
import java.time.LocalDate;

@Entity // Marca a classe como entidade do banco
@Table(name = "carros") // Nome da tabela no MySQL
public class Carro {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
    private int id;

    @Column(nullable = false)
    private String modelo;

    private int ano;
    private int quilometragem;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "controle_id") // FK
    private ControleTrocaOleo controleTrocaOleo;

    public Carro() {} // Construtor vazio obrigatório JPA

    public Carro(String modelo, int ano, int quilometragem, ControleTrocaOleo controleTrocaOleo) {
        this.modelo = modelo;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.controleTrocaOleo = controleTrocaOleo;
    }

    // Getters
    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public int getQuilometragem() { return quilometragem; }
    public ControleTrocaOleo getControleTrocaOleo() { return controleTrocaOleo; }

    // Setters
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAno(int ano) { this.ano = ano; }
    public void setQuilometragem(int quilometragem) { this.quilometragem = quilometragem; }
    public void setControleTrocaOleo(ControleTrocaOleo controleTrocaOleo) { this.controleTrocaOleo = controleTrocaOleo; }

    // Métodos utilitários
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
