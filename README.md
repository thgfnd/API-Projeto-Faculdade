Controle de Veículos - API e Frontend
Este é um projeto full-stack de uma aplicação para controle de veículos, desenvolvido como um projeto de faculdade. A aplicação permite o gerenciamento de uma frota de veículos, com controle de quilometragem e agendamento de troca de óleo, separando o acesso entre perfis de Administrador e Cliente.

✨ Funcionalidades
O sistema é dividido em dois painéis principais com diferentes níveis de acesso:

Painel do Administrador (ROLE_ADMIN)
Login Seguro: Acesso via autenticação.

Dashboard Completo: Visualização de todos os veículos cadastrados no sistema.

Gerenciamento de Usuários: Visualização de todos os usuários cadastrados (clientes e admins).

Cadastro de Veículos: Capacidade de adicionar um novo veículo e atribuí-lo a qualquer cliente existente.

CRUD de Veículos:

Editar: Alterar as informações de qualquer veículo (placa, modelo, ano, quilometragem).

Excluir: Remover um veículo do sistema.

Painel do Cliente (ROLE_CLIENTE)
Login Seguro: Acesso via autenticação.

Dashboard Pessoal: Visualização de apenas os veículos que lhe pertencem.

Autocadastro de Veículo: Capacidade de adicionar um novo veículo, que é automaticamente vinculado à sua conta.

Gerenciamento da Manutenção:

Visualizar o status da troca de óleo.

Registrar uma nova troca de óleo.

Atualizar a quilometragem atual do veículo.

Editar os intervalos de troca (por KM e por meses).

🛠️ Tecnologias Utilizadas
Backend
Java 21

Spring Boot 3.2.0

Spring Security: Para autenticação e autorização baseada em papéis (roles).

Spring Data JPA (Hibernate): Para persistência de dados e comunicação com o banco.

MySQL: Banco de dados relacional para armazenamento dos dados.

Maven: Gerenciador de dependências.

Frontend
HTML5

CSS3

JavaScript (ES6+): Manipulação do DOM e comunicação com a API via Fetch.

Arquitetura Multi-Page: Páginas separadas para Login, Painel do Admin e Painel do Cliente.

🚀 Como Executar o Projeto
Siga os passos abaixo para configurar e executar a aplicação em um ambiente de desenvolvimento.

Pré-requisitos
JDK 21 (ou superior) instalado.

Maven instalado.

MySQL Server instalado e em execução.

Git instalado.

1. Configuração do Banco de Dados
   Certifique-se de que seu servidor MySQL está rodando.

Crie um novo banco de dados (schema) chamado controle_veiculos.

SQL

CREATE DATABASE controle_veiculos;
As tabelas (carros, usuarios, controles_oleo) serão criadas automaticamente pelo Hibernate na primeira vez que a aplicação for iniciada, graças à configuração spring.jpa.hibernate.ddl-auto=create.

2. Configuração do Backend
   Clone o repositório para sua máquina local:

Bash

git clone https://github.com/thgfnd/API-Projeto-Faculdade.git
Navegue até a pasta do projeto backend:

Bash

cd API-Projeto-Faculdade/Projeto Controle API
Verifique o arquivo src/main/resources/application.properties e, se necessário, altere o spring.datasource.username e spring.datasource.password para corresponder às suas credenciais do MySQL.

Execute a aplicação usando o Maven:

Bash

mvn spring-boot:run
O backend estará rodando em http://localhost:8080.

3. Acessando a Aplicação
   Abra seu navegador de internet.

Navegue para http://localhost:8080.

A página de login será exibida.

Usuários de Teste
Dois usuários são criados automaticamente na primeira inicialização (DataLoader.java):

Administrador:

Login: admin@email.com

Senha: admin

Cliente:

Login: cliente@email.com

Senha: cliente

