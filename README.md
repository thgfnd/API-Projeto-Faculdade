# 🚗 Controle de Veículos - Projeto Acadêmico (Em Desenvolvimento)

Este projeto faz parte das atividades da disciplina de **Análise e Desenvolvimento de Sistemas**.  
O objetivo é desenvolver uma API em **Spring Boot** para gerenciar veículos e controlar a necessidade de troca de óleo com base na quilometragem.

⚠️ Projeto **em desenvolvimento** — novas funcionalidades estão sendo implementadas.

---

## 🎯 Objetivos do Projeto
- Praticar conceitos de **Programação Orientada a Objetos (POO)** em Java.  
- Desenvolver uma aplicação **RESTful API** utilizando Spring Boot.  
- Trabalhar com **operações CRUD** (Create, Read, Update, Delete).  
- Implementar regras de negócio relacionadas à **manutenção de veículos**.  

---

## 🚀 Como rodar o projeto
```bash
git clone https://github.com/thgfnd/controle-veiculos.git
cd controle-veiculos
mvn spring-boot:run
A aplicação sobe em: http://localhost:8080

📌 Endpoints principais
GET /veiculos → Lista todos os veículos

GET /veiculos/{id} → Busca veículo por ID

POST /veiculos → Registra novo veículo

PUT /veiculos/{id} → Atualiza informações do veículo

DELETE /veiculos/{id} → Remove veículo

POST /veiculos/{id}/troca-oleo → Informa a quilometragem atual e verifica se precisa trocar o óleo

📖 Exemplo de JSON
json
Copiar código
{
  "modelo": "Civic",
  "quilometragem": 12000,
  "controleTrocaOleo": {
    "kmTroca": 15000
  }
}
📅 Roadmap (em progresso)

 CRUD de veículos

 Controle de troca de óleo

 Persistência em banco de dados

📝 Observação
Este é um projeto acadêmico desenvolvido com fins de aprendizado.
O código poderá ser expandido e otimizado em versões futuras.
