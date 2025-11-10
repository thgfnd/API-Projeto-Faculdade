Controle de Veículos - API e Frontend
Este é um projeto full-stack de uma aplicação para controle de veículos, desenvolvido como um projeto de faculdade. A aplicação foi modernizada para incluir um sistema de notificação automática por e-mail, um painel de admin reativo em estilo card e uma interface com tema cyberpunk.

O sistema permite o gerenciamento de veículos com controle de quilometragem e troca de óleo, separando o acesso entre perfis de Administrador e Cliente.

✨ Funcionalidades
Painel do Administrador (ROLE_ADMIN)
Login Seguro: Acesso via autenticação Spring Security.

Dashboard Reativo: Visualização de todos os veículos da plataforma em formato de cards.

Status Rápido: Visualização do status da troca de óleo ("Em Dia" / "Pendente") diretamente no card de cada veículo.

Busca Rápida: Sistema de busca de veículos em tempo real por CPF do proprietário.

Gerenciamento de Usuários: Visualização de todos os clientes cadastrados.

Gerenciamento de Veículos:

Editar: Alterar as informações de qualquer veículo (placa, modelo, ano, quilometragem).

Excluir: Remover um veículo do sistema.

Ver Detalhes: Modal completo com todas as informações do veículo, do proprietário e do controle de óleo.

Painel do Cliente (ROLE_CLIENTE)
Login Seguro: Acesso via autenticação.

Cadastro de Usuário: Tela de auto-cadastro (Sign-up) com validação de campos (senha, CPF, e-mail).

Dashboard Pessoal: Visualização apenas dos veículos que pertencem ao cliente.

Status Rápido: Visualização do status da troca de óleo ("Em Dia" / "Pendente") na lista de veículos.

Autocadastro de Veículo: Capacidade de adicionar um novo veículo, que é automaticamente vinculado à sua conta.

Gerenciamento da Manutenção:

Visualizar o status detalhado da troca de óleo.

Registrar uma nova troca de óleo (resetando os contadores).

Atualizar a quilometragem atual do veículo.

Editar os intervalos de troca (por KM e por meses).

📧 Sistema de Notificação Automática por E-mail
A aplicação conta com um sistema de e-mails transacionais e agendados:

Confirmação de Cadastro: O cliente recebe um e-mail de boas-vindas ao adicionar um novo veículo.

Lembrete de KM: Um e-mail agendado (dias 1 e 15) é enviado solicitando que o cliente atualize a quilometragem do veículo.

Alerta de Vencimento: Um e-mail agendado (verificação diária) notifica o cliente se a troca de óleo estiver vencida (seja por KM ou por Meses).

🛠️ Tecnologias Utilizadas
Backend
Java 21

Spring Boot 3.2.0

Spring Security: Para autenticação e autorização baseada em papéis (roles).

Spring Data JPA (Hibernate): Para persistência de dados.

Spring Boot Mail: Para envio de e-mails transacionais e agendados (@Scheduled, @Async).

MySQL: Banco de dados relacional.

Maven: Gerenciador de dependências.

Frontend
HTML5 e CSS3

JavaScript (ES6+): Manipulação do DOM e comunicação com a API via Fetch.

UI Temática: Interface com estilo cyberpunk (dark mode com neon) em todas as telas.

Arquitetura Multi-Page: Páginas separadas para Login, Cadastro, Admin e Cliente.

🚀 Como Executar o Projeto
Siga os passos abaixo para configurar e executar a aplicação.

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
As tabelas serão criadas automaticamente pelo Hibernate na primeira inicialização (ddl-auto=update ou create).

2. Configuração do Backend
Clone o repositório:

Bash

git clone https://github.com/thgfnd/API-Projeto-Faculdade.git
Navegue até a pasta do projeto:

Bash

cd API-Projeto-Faculdade
Abra o arquivo src/main/resources/application.properties. O projeto não irá iniciar sem as configurações corretas de Banco de Dados e E-mail.

Configure o Banco de Dados (Obrigatório): Altere o username e password para as suas credenciais do MySQL. IMPORTANTE: A URL precisa do parâmetro allowPublicKeyRetrieval=true para funcionar com os drivers MySQL mais recentes.

Properties

spring.datasource.url=jdbc:mysql://localhost:3306/controle_veiculos?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=sua_senha_do_mysql
Configure o Serviço de E-mail (Obrigatório): O sistema de e-mail usa o Gmail. Você precisa gerar uma "Senha de App" na sua conta Google (senhas normais não funcionam mais).

Properties

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email-aqui@gmail.com
spring.mail.password=sua-senha-de-app-de-16-letras-aqui
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
3. Acessando a Aplicação
Execute a aplicação usando o Maven:

Bash

mvn spring-boot:run
O backend estará rodando em http://localhost:8080.

Abra seu navegador e acesse http://localhost:8080.

Usuários de Teste
Dois usuários são criados automaticamente na primeira inicialização (via DataLoader.java):

Administrador:

Login: admin@email.com

Senha: admin

Cliente:

Login: cliente@email.com

Senha: cliente

📸 Telas do Projeto
Tela de Cadastro (Nova)
<img width="890" height="765" alt="tela-cadastro" src="https://github.com/user-attachments/assets/ddd77899-a3bf-403a-9b8a-6a3e7949615d" />

Tela de Login
<img width="885" height="798" alt="tela-login" src="https://github.com/user-attachments/assets/260d3696-e4e7-4b9c-bd05-abdc7ed39052" />

Tela do Admin (Cards e Busca)
<img width="938" height="914" alt="tela-admin" src="https://github.com/user-attachments/assets/47a6fd04-d17a-40ad-a249-459712b16188" />

Sub-tela do Admin (Modal de Detalhes)
<img width="880" height="908" alt="subtela-admin" src="https://github.com/user-attachments/assets/8cadbbef-d377-4922-960d-7fd9cc774cfc" />

Tela do Cliente (com Status)
<img width="867" height="905" alt="tela-cliente" src="https://github.com/user-attachments/assets/8c72c479-bf2c-4e66-af7b-89f01c8266ee" />

Sub-tela do Cliente (Modal de Detalhes)
<img width="742" height="853" alt="subtela-cliente" src="https://github.com/user-attachments/assets/416a7688-def1-441a-bad9-37d053ac94b1" />
