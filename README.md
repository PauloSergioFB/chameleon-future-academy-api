![Banner](./docs/banner.png)

![Java 17](https://img.shields.io/badge/Java%2017-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
[![FIAP](https://img.shields.io/badge/FIAP-ED145B?style=for-the-badge&logoColor=white)]()
[![Oracle](https://img.shields.io/badge/Oracle%20Cloud-F80000?style=for-the-badge&logo=oracle&logoColor=white)]()

O Chameleon Future Academy é uma plataforma educacional criada para ajudar pessoas cujas profissões estão sendo impactadas ou substituídas pelos avanços tecnológicos. Seu objetivo é facilitar a migração de carreira, oferecendo cursos focados em habilidades relevantes para o mercado do futuro.

A aplicação permite criar e gerenciar contas de usuário, consultar cursos, acessar aulas e atividades, realizar matrículas e conquistar badges ao concluir formações. Tudo isso compondo um perfil acadêmico dinâmico e em constante evolução.

> Este repositório contém os arquivos da API do Chameleon Future Academy, desenvolvida com Spring Boot.

---

[Video Pitch](#vídeo-pitch) | [Demonstração da Solução](#demonstração-da-solução) | [Deploy](#deploy) | [Endpoints](#endpoints) | [Setup do Projeto](#setup-do-projeto) | [Requisições de Teste](#requisições-de-teste) | [Stack Tecnológica](#stack-tecnológica) | [Desenvolvedores](#desenvolvedores)

---

## Vídeo Pitch

**Assista no YouTube:** [https://youtu.be/jwT6pl8h4AI](https://youtu.be/jwT6pl8h4AI)

## Demonstração da Solução

**Assista no YouTube:** [https://youtu.be/_XM47GfITTA](https://youtu.be/_XM47GfITTA)

## Deploy

A API está disponível publicamente em:  

**Endpoint da API:** [http://paulosergiofb.com.br:8080](http://paulosergiofb.com.br:8080)  
**Documentação Swagger:** [http://paulosergiofb.com.br:8080/swagger-ui/index.html](http://paulosergiofb.com.br:8080/swagger-ui/index.html)

## Endpoints

Os endpoints foram definidos com base nas necessidades reais do app mobile, incluindo rotas de busca e endpoints detalhados para facilitar o consumo dos dados pelo cliente. A ausência de alguns endpoints CRUD ocorre porque esses recursos não são responsabilidade desta API: sua gestão será feita pela API de back office (.NET), utilizada exclusivamente pelos administradores do sistema.

A seguir estão listados os principais endpoints disponíveis na API Front Office (Este repositório).

### Autenticação

```
POST   /api/v1/auth         Faz login e retorna o token de acesso  
POST   /api/v1/auth/refresh Gera um novo token quando o atual expirar  
```

### Usuários

```
GET    /api/v1/users/me             Retorna as informações da conta do usuário logado    
GET    /api/v1/users/me/enrollments Lista todas as matrículas do usuário    
GET    /api/v1/users/me/badges      Lista todas as badges conquistadas pelo usuário    
GET    /api/v1/users/me/profile     Retorna o perfil completo do usuário, incluindo cursos concluídos, badges e progresso    
POST   /api/v1/users                Cria uma nova conta de usuário    
PUT    /api/v1/users/{id}           Atualiza os dados de um usuário    
DELETE /api/v1/users/{id}           Remove a conta de um usuário    
```

### Cursos

```
GET /api/v1/courses               Lista todos os cursos com paginação  
GET /api/v1/courses/search        Pesquisa cursos por título, autor ou tag  
GET /api/v1/courses/{id}          Lista as badges que podem ser conquistadas nesse curso  
GET /api/v1/courses/{id}/tags     Lista as tags associadas ao curso  
GET /api/v1/courses/{id}/contents Lista todas as aulas e atividades do curso  
GET /api/v1/courses/{id}/badges   Lista as badges que podem ser conquistadas nesse curso  
GET /api/v1/courses/{id}/details  Retorna as informações completas e aprofundadas do curso  
```

### Tags

```
GET /api/v1/tags Lista todas as tags cadastradas  
```

### Conteúdos

```
GET /api/v1/contents/{id}/lesson   Busca a aula vinculada a um conteúdo específico  
GET /api/v1/contents/{id}/activity Busca a atividade vinculada a um conteúdo específico  
```

### Matrículas

```
POST   /api/v1/enrollments      Cria uma matrícula do usuário em um curso  
PUT    /api/v1/enrollments/{id} Atualiza o progresso ou status da matrícula  
DELETE /api/v1/enrollments/{id} Remove uma matrícula  
```

### Como se autenticar

Para acessar as rotas protegidas da API (todas as rotas exceto **POST /api/v1/users**, **/api/v1/auth** e **/swagger**), é necessário realizar login primeiro.  

Caso ainda não tenha conta, cadastre-se usando:
```
POST /users
Content-Type: application/json

{
  "full_name": "nomedeusuario",
  "email": "usuario@example.com",
  "password": "suasenha"
}
```

Faça login no endpoint:
```
POST /auth
Content-Type: application/json

{
  "email": "usuario@example.com",
  "password": "suasenha"
}
```

**Resposta esperada**
```
{
  "token": "<jwt_token>",
  "refreshToken": "<jwt_refresh_token>"
}
```

Após receber o token, todas as requisições protegidas devem incluir o cabeçalho:  
```
Authorization: Bearer <jwt_token>
```

Exemplo de uso do token:
```
GET /users/me
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Paginação, Ordenação e Filtros

Alguns endpoints GET permitem enviar parâmetros para controlar a quantidade de resultados, a ordem da listagem e filtros de busca. Esses valores são enviados como query params na URL.

Exemplo real:
```
GET /courses/search?page=0&size=10&orderBy=title&direction=asc&title=java
```

## Setup do Projeto

### Instalação Local

Antes de iniciar, certifique-se de ter instalado:

- **Git**
- **Java** (versão 17)
- **Maven (mvn)**

#### 1. Clonar Repositório
```bash
# Clonar o repositório
git clone https://github.com/paulosergiofb/chameleon-future-academy-api.git

# Acessar o diretório
cd chameleon-future-academy-api

# Instalar as dependências
mvn compile
```

#### 2. Configurar o Ambiente

Crie um arquivo .env na raiz do projeto com o seguinte conteúdo (substitua pelas suas próprias credenciais e configurações):

```bash
DB_URL=<jdbc_url_do_banco>
DB_USERNAME=<seu_usuario_do_banco>
DB_PASSWORD=<sua_senha_do_banco>

JWT_SECRET=<sua_chave_secreta>
```
**Observação:** JWT_SECRET é a chave usada para assinar e validar os tokens JWT. Ela deve ser longa e secreta, pois garante a segurança da autenticação.

#### 3. Iniciar o projeto

```bash
mvn spring-boot:run
```

Após a inicialização, a API estará disponível em: http://localhost:8080  
A documentação interativa (Swagger UI) pode ser acessada em: http://localhost:8080/swagger-ui/index.html

### Execução Via Docker

Antes de iniciar, certifique-se de ter instalado:

- **Git**
- **Docker**

#### 1. Clonar Repositório
```bash
# Clonar o repositório
git clone https://github.com/paulosergiofb/chameleon-future-academy-api.git

# Acessar o diretório
cd chameleon-future-academy-api
```

#### 2. Configurar o Ambiente

Crie um arquivo .env na raiz do projeto com o seguinte conteúdo (substitua pelos suas próprias credenciais e configurações):

```bash
DB_URL=<jdbc_url_do_banco>
DB_USERNAME=<seu_usuario_do_banco>
DB_PASSWORD=<sua_senha_do_banco>

JWT_SECRET=<sua_chave_secreta>
```
**Observação:** JWT_SECRET é a chave usada para assinar e validar os tokens JWT. Ela deve ser longa e secreta, pois garante a segurança da autenticação.

#### 3. Iniciar o projeto

```bash
# Construir a imagem do projeto
docker build -t chameleon-future-academy-api .

# Executar o container (carregando as variáveis de .env)
docker run -it --rm --env-file .env -p 8080:8080 chameleon-future-academy-api
```

Após a inicialização, a API estará disponível em: http://localhost:8080  
A documentação interativa (Swagger UI) pode ser acessada em: http://localhost:8080/swagger-ui/index.html

## Requisições de Teste

O projeto inclui um script `requests.sh` com exemplos de requisições via `curl`.

## Stack Tecnológica

O projeto utiliza as seguintes tecnologias:

- Java 17 - Linguagem principal da API.  
- Maven - Gerenciador de dependências e build.  
- Spring Boot - Framework para configuração e execução da aplicação.  
- Spring Data JPA - Persistência e mapeamento de dados.  
- Spring Security — Autenticação e autorização via JWT.  
- Oracle Database - Banco de dados relacional do projeto.  
- Docker - Conteinerização e padronização do ambiente.  

## Desenvolvedores

[@AntonioDeLuca](https://github.com/antoniodeluca) - Desenvolvedor Backend  
[@EnzoAzevedo](https://github.com/enzoazevedo) - Desenvolvedor Backend  
[@PauloSérgioFB](https://github.com/paulgramador) - Desenvolvedor Mobile
