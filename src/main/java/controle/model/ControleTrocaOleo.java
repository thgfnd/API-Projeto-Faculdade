package controle.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ControleTrocaOleo {
    private LocalDate dataUltimaTroca;
    private int kmUltimaTroca;
    private int intervaloKm;
    private int intervaloMeses;

    public ControleTrocaOleo(int kmUltimaTroca, int intervaloKm, LocalDate dataUltimaTroca, int intervaloMeses) {
        this.kmUltimaTroca = kmUltimaTroca;
        this.intervaloKm = intervaloKm;
        this.dataUltimaTroca = dataUltimaTroca;
        this.intervaloMeses = intervaloMeses;
    }

    public boolean precisaTrocar(int kmAtual) {
        int kmExcedente = kmAtual - (kmUltimaTroca + intervaloKm);
        long mesesExcedentes = ChronoUnit.MONTHS.between(dataUltimaTroca, LocalDate.now()) - intervaloMeses;
        return kmExcedente > 0 || mesesExcedentes > 0;
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
        kmUltimaTroca = kmAtual;
        dataUltimaTroca = LocalDate.now();
    }

    // Getters e setters
    public int getKmUltimaTroca() { return kmUltimaTroca; }
    public int getIntervaloKm() { return intervaloKm; }
    public LocalDate getDataUltimaTroca() { return dataUltimaTroca; }
    public int getIntervaloMeses() { return intervaloMeses; }
}
