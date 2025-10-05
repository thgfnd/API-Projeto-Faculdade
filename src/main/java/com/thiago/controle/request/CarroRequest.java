package com.thiago.controle.request;

// Esta classe simples serve apenas para receber os dados do formulário do frontend.
public class CarroRequest {
    private String placa;
    private String modelo;
    private int ano;
    private int quilometragem;

    // Dados para o ControleTrocaOleo
    private String dataUltimaTroca; // Recebemos a data como String
    private int kmUltimaTroca;
    private int intervaloKm;
    private int intervaloMeses;

    // Getters e Setters para todos os campos...
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public int getQuilometragem() { return quilometragem; }
    public void setQuilometragem(int quilometragem) { this.quilometragem = quilometragem; }
    public String getDataUltimaTroca() { return dataUltimaTroca; }
    public void setDataUltimaTroca(String dataUltimaTroca) { this.dataUltimaTroca = dataUltimaTroca; }
    public int getKmUltimaTroca() { return kmUltimaTroca; }
    public void setKmUltimaTroca(int kmUltimaTroca) { this.kmUltimaTroca = kmUltimaTroca; }
    public int getIntervaloKm() { return intervaloKm; }
    public void setIntervaloKm(int intervaloKm) { this.intervaloKm = intervaloKm; }
    public int getIntervaloMeses() { return intervaloMeses; }
    public void setIntervaloMeses(int intervaloMeses) { this.intervaloMeses = intervaloMeses; }
}