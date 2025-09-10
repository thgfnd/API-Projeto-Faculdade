# 🚗 Controle de Veículos - Projeto Acadêmico (Em Desenvolvimento)

Este projeto faz parte das atividades da disciplina de Análise e Desenvolvimento de Sistemas.  
O objetivo é desenvolver uma API em Spring Boot para gerenciar veículos e controlar a necessidade de troca de óleo com base na quilometragem, com persistência em **banco de dados MySQL**.

⚠️ Projeto em desenvolvimento — novas funcionalidades estão sendo implementadas.

---

## 🎯 Objetivos do Projeto

- Praticar conceitos de Programação Orientada a Objetos (POO) em Java.  
- Desenvolver uma aplicação RESTful API utilizando Spring Boot.  
- Trabalhar com operações CRUD (Create, Read, Update, Delete).  
- Implementar regras de negócio relacionadas à manutenção de veículos e controle de troca de óleo.  

---

## 🚀 Como rodar o projeto

```bash
git clone https://github.com/thgfnd/controle-veiculos.git
cd controle-veiculos
mvn spring-boot:run
A aplicação sobe em: http://localhost:8080

Observação: É necessário ter o MySQL rodando e configurado no application.properties.

📌 Endpoints principais
Método	Endpoint	Descrição
GET	/veiculos	Lista todos os veículos
GET	/veiculos/{id}	Busca veículo por ID
POST	/veiculos	Registra novo veículo
PUT	/veiculos/{id}/quilometragem	Atualiza a quilometragem do veículo
DELETE	/veiculos/{id}	Remove veículo
POST	/veiculos/{id}/registrar-oleo	Registra a troca de óleo
GET	/veiculos/{id}/status-oleo	Consulta se o veículo precisa trocar o óleo

📖 Exemplo de JSON para registro de veículo
json
Copiar código
{
  "modelo": "Civic",
  "quilometragem": 12000,
  "controleTrocaOleo": {
    "kmTroca": 15000
  }
}
⚠️ A necessidade de troca de óleo é calculada com base na quilometragem do veículo.

📅 Roadmap (em progresso)
CRUD de veículos

Controle de troca de óleo

Persistência em banco de dados

Regras de negócio automáticas (ex: alerta de troca de óleo)

📝 Observação
Este é um projeto acadêmico desenvolvido com fins de aprendizado.
O código poderá ser expandido e otimizado em versões futuras.
