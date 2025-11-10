package com.thiago.controle.service;

import com.thiago.controle.model.Carro;
import com.thiago.controle.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailRemetente;

    /**
     * Método base para enviar qualquer e-mail simples.
     */
    public void enviarEmailSimples(String para, String assunto, String texto) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(emailRemetente);
            mensagem.setTo(para);
            mensagem.setSubject(assunto);
            mensagem.setText(texto);
            mailSender.send(mensagem);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail para " + para + ": " + e.getMessage());
        }
    }

    /**
     * Envia o e-mail de confirmação de cadastro de veículo.
     */
    @Async
    public void enviarEmailConfirmacaoNovoVeiculo(Usuario cliente, Carro carro) {
        System.out.println("Iniciando envio de e-mail de confirmação para: " + cliente.getEmail());

        String emailCliente = cliente.getEmail();
        String nomeCliente = cliente.getNome();
        String modeloCarro = carro.getModelo();
        String placaCarro = carro.getPlaca();

        String assunto = "Seu veículo foi cadastrado com sucesso!";
        String texto = String.format(
                "Olá %s,\n\n" +
                        "Seu veículo %s, placa %s, foi cadastrado com sucesso em nosso sistema!\n\n" +
                        "A partir de agora, você pode acompanhar os prazos para a próxima troca de óleo e " +
                        "gerenciar a manutenção do seu carro diretamente pelo nosso portal.\n\n" +
                        "Atenciosamente,\n" +
                        "Sua Oficina de Confiança",
                nomeCliente, modeloCarro, placaCarro
        );

        enviarEmailSimples(emailCliente, assunto, texto);
    }
}