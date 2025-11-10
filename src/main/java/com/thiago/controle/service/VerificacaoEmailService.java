package com.thiago.controle.service;

import com.thiago.controle.model.Carro;
import com.thiago.controle.model.ControleTrocaOleo;
import com.thiago.controle.model.Usuario;
import com.thiago.controle.repository.CarroRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VerificacaoEmailService {

    private final EmailService emailService;
    private final CarroRepository carroRepository;

    public VerificacaoEmailService(EmailService emailService, CarroRepository carroRepository) {
        this.emailService = emailService;
        this.carroRepository = carroRepository;
    }

    /**
     * Roda todo dia 1º e dia 15, às 9h da manhã.
     * Pede ao cliente a KM atual.
     */
    @Scheduled(cron = "0 0 9 1,15 * ?")
    public void enviarLembreteKmQuinzenal() {
        // ... (Este método está correto e permanece o mesmo)
        System.out.println("Iniciando tarefa agendada: E-mail Quinzenal de KM...");
        List<Carro> carros = carroRepository.findAll();

        for (Carro carro : carros) {
            Usuario cliente = carro.getUsuario();
            String emailCliente = cliente.getEmail();
            String nomeCliente = cliente.getNome();
            String modeloCarro = carro.getModelo();
            String placaCarro = carro.getPlaca();

            String assunto = "Controle de Veículo: Como está sua quilometragem?";
            String texto = String.format(
                    "Olá %s,\n\n" +
                            "Este é um lembrete automático do nosso sistema de controle de veículos.\n" +
                            "Para manter o status da sua troca de óleo sempre atualizado, gostaríamos de saber a quilometragem atual do seu %s (Placa: %s).\n\n" +
                            "Você pode acessar nosso portal para atualizar a KM a qualquer momento!\n\n" +
                            "Atenciosamente,\n" +
                            "Sua Oficina de Confiança",
                    nomeCliente, modeloCarro, placaCarro
            );

            emailService.enviarEmailSimples(emailCliente, assunto, texto);
        }
    }

    /**
     * Roda todo dia, às 8h da manhã.
     * Verifica se a troca de óleo venceu por MESES ou KM.
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void verificarTrocaDeOleoVencida() {
        System.out.println("Iniciando tarefa agendada: Verificação de Óleo Vencido...");
        List<Carro> carros = carroRepository.findAll();

        for (Carro carro : carros) {
            ControleTrocaOleo controle = carro.getControleTrocaOleo();
            if (controle == null) {
                continue; // Pula carros sem controle de óleo
            }

            // --- LÓGICA DE CÁLCULO ATUALIZADA ---
            // 1. Pega a KM atual registrada no carro
            int kmAtual = carro.getQuilometragem();

            // 2. Calcula os excedentes
            int kmExcedente = kmAtual - (controle.getKmUltimaTroca() + controle.getIntervaloKm());
            long mesesExcedentes = ChronoUnit.MONTHS.between(controle.getDataUltimaTroca(), LocalDate.now()) - controle.getIntervaloMeses();
            // --- FIM DA LÓGICA ATUALIZADA ---


            // 3. Verifica se venceu (por KM OU Meses) E se o e-mail ainda NÃO foi enviado
            if ((kmExcedente > 0 || mesesExcedentes > 0) && !controle.isNotificacaoMesVencidoEnviada()) {

                Usuario cliente = carro.getUsuario();
                String emailCliente = cliente.getEmail();
                String nomeCliente = cliente.getNome();

                // --- LÓGICA DE MENSAGEM DINÂMICA ---
                String motivoVencimento = "";
                if (kmExcedente > 0) {
                    motivoVencimento = String.format("o limite de %d KM foi ultrapassado", kmExcedente);
                }
                if (mesesExcedentes > 0) {
                    if (!motivoVencimento.isEmpty()) {
                        motivoVencimento += " e ";
                    }
                    motivoVencimento += String.format("o prazo de %d meses foi ultrapassado", mesesExcedentes);
                }
                // --- FIM DA MENSAGEM DINÂMICA ---

                String assunto = "⚠️ ALERTA: Troca de Óleo Vencida!";
                String texto = String.format(
                        "Olá %s,\n\n" +
                                "Detectamos em nosso sistema que a troca de óleo do seu veículo %s (Placa: %s) está vencida, pois %s.\n\n" +
                                "É crucial realizar a troca para garantir a saúde do motor do seu veículo.\n\n" +
                                "Por favor, entre em contato conosco para agendar sua manutenção o mais rápido possível.\n\n" +
                                "Atenciosamente,\n" +
                                "Sua Oficina",
                        nomeCliente, carro.getModelo(), carro.getPlaca(), motivoVencimento
                );

                emailService.enviarEmailSimples(emailCliente, assunto, texto);

                // 4. Marca que o e-mail foi enviado para não enviar de novo
                controle.setNotificacaoMesVencidoEnviada(true);
                carroRepository.save(carro); // Salva a alteração no banco
            }
        }
    }
}