package com.thiago.controle.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "controles_oleo")
public class ControleTrocaOleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate dataUltimaTroca;
    private int kmUltimaTroca;
    private int intervaloKm;
    private int intervaloMeses;
    private boolean notificacaoMesVencidoEnviada = false;

    public ControleTrocaOleo() {}

    public ControleTrocaOleo(int kmUltimaTroca, int intervaloKm, LocalDate dataUltimaTroca, int intervaloMeses) {
        this.kmUltimaTroca = kmUltimaTroca;
        this.intervaloKm = intervaloKm;
        this.dataUltimaTroca = dataUltimaTroca;
        this.intervaloMeses = intervaloMeses;
        this.notificacaoMesVencidoEnviada = false; // Garantir que começa como falso
    }

    public String statusTroca(int kmAtual) {
        StringBuilder sb = new StringBuilder();
        int kmExcedente = kmAtual - (kmUltimaTroca + intervaloKm);
        long mesesExcedentes = ChronoUnit.MONTHS.between(dataUltimaTroca, LocalDate.now()) - intervaloMeses;

        if (kmExcedente > 0 || mesesExcedentes > 0) {
            sb.append("⚠️ Troca de óleo necessária!\n");
            if (kmExcedente > 0) sb.append("Você ultrapassou ").append(kmExcedente).append(" km do limite.\n");
            if (mesesExcedentes > 0) sb.append("Você ultrapassou ").append(mesesExcedentes).append(" meses do prazo.\n");
        } else {
            sb.append("✅ Óleo em dia.\n");
            sb.append("Faltam ").append(-kmExcedente).append(" km para próxima troca.\n");
            sb.append("Faltam ").append(-mesesExcedentes).append(" meses para próxima troca.\n");
        }
        return sb.toString();
    }

    public void registrarTroca(int kmAtual) {
        this.kmUltimaTroca = kmAtual;
        this.dataUltimaTroca = LocalDate.now();
        this.notificacaoMesVencidoEnviada = false; // <-- IMPORTANTE: Resetar a flag!
    }

    // Getters
    public int getId() { return id; }
    public int getKmUltimaTroca() { return kmUltimaTroca; }
    public int getIntervaloKm() { return intervaloKm; }
    public LocalDate getDataUltimaTroca() { return dataUltimaTroca; }
    public int getIntervaloMeses() { return intervaloMeses; }
    public boolean isNotificacaoMesVencidoEnviada() {return notificacaoMesVencidoEnviada;}

    // Setters
    public void setIntervaloKm(int intervaloKm) {
        this.intervaloKm = intervaloKm;
    }

    public void setIntervaloMeses(int intervaloMeses) {
        this.intervaloMeses = intervaloMeses;
    }

    public void setNotificacaoMesVencidoEnviada(boolean notificacaoMesVencidoEnviada) {
        this.notificacaoMesVencidoEnviada = notificacaoMesVencidoEnviada;
    }
}