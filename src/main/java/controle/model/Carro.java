package controle.model;

public class Carro {
    private int id;
    private String modelo;
    private int ano;
    private int quilometragem;
    private ControleTrocaOleo controleTrocaOleo;

    public Carro(int id, String modelo, int ano, int quilometragem, ControleTrocaOleo controleTrocaOleo) {
        this.id = id;
        this.modelo = modelo;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.controleTrocaOleo = controleTrocaOleo;
    }

    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public int getQuilometragem() { return quilometragem; }
    public ControleTrocaOleo getControleTrocaOleo() { return controleTrocaOleo; }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }
}
