<div align="center">

# 🧭 Vagando — API

**Plataforma de vagas e candidaturas** — o backend REST do sistema Vagando.

Cadastro de empresas e candidatos, publicação de vagas, candidaturas com
**match automático por skills** e autenticação via JWT.

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Flyway-4169E1?logo=postgresql&logoColor=white)
![Security](https://img.shields.io/badge/Auth-JWT%20%2F%20BCrypt-000000?logo=jsonwebtokens&logoColor=white)
![OpenAPI](https://img.shields.io/badge/Docs-Swagger%20UI-85EA2D?logo=swagger&logoColor=black)

</div>

---

## ✨ O que ele faz

- 🔐 **Registro e login** de usuários (candidato ou empresa) com senha em BCrypt e sessão *stateless* via JWT
- 👤 **Perfis** de candidato (CPF, currículo, skills) e empresa (CNPJ, descrição)
- 📢 **Vagas** — empresas criam, editam, listam e ativam/desativam vagas com requisitos e skills
- 📝 **Candidaturas** — candidatos se aplicam a vagas (uma por vaga) e acompanham o status
- 🎯 **Match por skills** — cada candidatura recebe um *score* (0–100) calculado pela % de skills da vaga que o candidato possui
- 📊 **Dashboard** da empresa — vagas ativas, totais de candidaturas e distribuição por status
- 🩺 **Health check** e **documentação OpenAPI** prontos para uso

## 🧱 Stack

| Camada | Tecnologia |
| --- | --- |
| Linguagem | **Java 25** |
| Framework | **Spring Boot 4.0** (Web MVC, Data JPA, Security) |
| Auth | **java-jwt (Auth0)** + BCrypt |
| Banco | **PostgreSQL** + **Flyway** (migrations) |
| Docs | **SpringDoc OpenAPI** (Swagger UI) |
| Utilitários | **Lombok** |
| Testes | Spring Boot Test, Spring Security Test |

## 🏛️ Arquitetura

O projeto segue **Clean Architecture**: o domínio não conhece o mundo externo.
A infraestrutura implementa os *gateways* do domínio via *adapters* + *mappers*.

```
        HTTP  ─────────────►  Controllers        (infrastructure/controller)
                                    │
                                    ▼
                              Interactors         (app/interactor)      ← regras de negócio
                                    │  usa
                                    ▼
                               Gateways           (app/gateway)         ← interfaces (portas)
                                    ▲  implementa
                                    │
                               Adapters           (infrastructure/adapter)
                                    │  Mappers ↔ Entities ↔ Repositories (Spring Data JPA)
                                    ▼
                              PostgreSQL
```

Cada `interactor` é ligado ao seu `adapter` por um `@Configuration` em
`infrastructure/config`, mantendo o domínio livre de dependências do Spring.

### Estrutura de pastas

```
src/main/java/com/vic/vagando
├── app/                      # núcleo independente de framework
│   ├── domain/               # entidades de domínio, inputs, outputs, filtros
│   ├── gateway/              # portas (interfaces)
│   ├── interactor/           # casos de uso / regras de negócio
│   ├── exception/            # exceções de negócio
│   └── util/                 # CPF, CNPJ, CalculateScore
└── infrastructure/           # detalhes técnicos
    ├── controller/           # endpoints REST
    ├── auth/                 # login, registro, TokenService
    ├── adapter/ + mapper/    # implementação dos gateways + conversões
    ├── entity/               # entidades JPA
    ├── persistence/          # repositórios Spring Data
    └── config/               # wiring, security, error handler
src/main/resources/db/migration  # migrations Flyway (V1 tabelas, V2 skills, V3 coluna active)
```

## 🔌 Endpoints

> Base URL local: `http://localhost:8080`

### 🌐 Públicos (sem token)

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/health` | Health check (retorna `OK`) |
| `POST` | `/auth/register` | Registra um usuário (candidato ou empresa) |
| `POST` | `/auth/login` | Autentica e retorna `{ token, role }` |
| `GET` | `/skills` | Lista paginada de skills disponíveis |
| `GET` | `/candidate/jobs` | Vitrine pública de vagas (auth opcional: marca `candidateApplied`) |

### 🔒 Autenticados

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/auth/get-user` | Perfil do usuário logado |
| **Candidato** | | |
| `GET` | `/candidate/` | Dados do candidato logado |
| `PATCH` | `/candidate/update` | Atualiza perfil do candidato |
| `POST` | `/candidate/{jobId}/aplicar` | Candidata-se a uma vaga (calcula o *score*) |
| `GET` | `/candidate/applications` | Candidaturas do candidato (paginado) |
| `GET` | `/candidate/applieds-jobs` | Vagas às quais o candidato se aplicou |
| **Empresa** | | |
| `PATCH` | `/company/update` | Atualiza perfil da empresa |
| `POST` | `/company/job` | Cria uma vaga |
| `PATCH` | `/company/job` | Edita uma vaga |
| `GET` | `/company/job` | Lista as vagas da empresa (filtro por `title`) |
| `GET` | `/company/{jobId}/applications` | Candidaturas de uma vaga |
| `PATCH`| `/company/{jobId}/applications/{applicationId}?status=` | Aprova/rejeita uma candidatura |
| `GET` | `/company/applications` | Todas as candidaturas às vagas da empresa (com filtro) |
| `GET` | `/company/dashboard` | Métricas do painel da empresa |
| **Geral** | | |
| `GET` | `/applications/status` | Lista os status possíveis de candidatura |

📖 Documentação interativa: **Swagger UI** em `/swagger-ui/index.html` · OpenAPI em `/v3/api-docs`.

## 🔐 Autenticação

Fluxo *stateless* baseado em JWT:

1. `POST /auth/register` cria o usuário (senha salva com BCrypt).
2. `POST /auth/login` valida as credenciais e devolve `{ token, role }`.
3. Endpoints protegidos exigem o header de autorização:

```http
Authorization: Bearer <token>
```

> O `SecurityFilter` aceita o token **com ou sem** o prefixo `Bearer `.

## 🗄️ Banco de dados

PostgreSQL com migrations gerenciadas pelo **Flyway**. Tabelas principais:

| Tabela | Papel |
| --- | --- |
| `users` | Credenciais e `role` (`CANDIDATE` / `COMPANY` / `ADMIN`) |
| `candidates` / `companies` | Perfis (1:1 com `users`) |
| `skills` | Catálogo de competências (semeado na V2) |
| `candidate_skills` / `job_skills` | Skills do candidato e da vaga (N:N) |
| `jobs` | Vagas (com coluna `active` para abrir/fechar) |
| `applications` | Candidaturas com `status` e `score` (única por candidato+vaga) |

## ⚙️ Configuração

O perfil ativo padrão é **`development`** (usado quando `SPRING_PROFILES_ACTIVE` não está definido).

**Perfil `development`** (`application-development.yaml`) — Postgres local:

- URL: `jdbc:postgresql://localhost:5432/vagando`
- Usuário: `admin` · Senha: `123`
- JWT secret: `mysecretkey`

**Perfil padrão** (`application.yaml`) — espera variáveis de ambiente:

| Variável | Descrição |
| --- | --- |
| `DATABASE_URL` | URL JDBC do Postgres |
| `DATABASE_USERNAME` | Usuário do banco |
| `DATABASE_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Segredo de assinatura do JWT |

## ▶️ Rodando localmente (Windows)

**1. Suba o PostgreSQL** e garanta que o banco `vagando` existe.

**2. Rode a aplicação:**

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=development"
```

ou via variável de ambiente:

```powershell
$env:SPRING_PROFILES_ACTIVE='development'; .\mvnw.cmd spring-boot:run
```

**3. Acesse:**

- API → `http://localhost:8080`
- Swagger UI → `http://localhost:8080/swagger-ui/index.html`

## 🧪 Testes

```powershell
.\mvnw.cmd test
```

Cobrem os *interactors* de usuário e empresa (`app/*Test.java`), com *factories* de teste em `test/.../util`.

## 🚀 Fluxo de exemplo

1. `POST /auth/register` → cria candidato ou empresa
2. `POST /auth/login` → copie o `token`
3. Chame os endpoints protegidos com `Authorization: Bearer <token>`
4. Empresa publica uma vaga → candidato se aplica → *score* é calculado automaticamente

## 📝 Notas

- DTOs, interactors, gateways e mappers mantêm o domínio independente da persistência.
- Relacionamentos bidirecionais evitam `toString`/`equals`/`hashCode` recursivos.
- O frontend (Next.js) vive no repositório **`sistema-vagas-next`** e consome esta API.
