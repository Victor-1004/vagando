# Vagando

Vagando is a Spring Boot REST API for a job marketplace.
It supports user registration and login with JWT, company and candidate profiles, and company job management.

## Tech stack

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Security
- Spring Data JPA
- Flyway
- PostgreSQL
- Lombok
- SpringDoc OpenAPI
- java-jwt

## Main features

- User registration and login
- JWT-based authentication
- Candidate profile update
- Company profile update
- Company job creation and listing
- Health check endpoint

## Authentication

The API uses JWT.

- Login endpoint: `POST /auth/login`
- Register endpoint: `POST /auth/register`
- Protected endpoints expect the header:

```http
Authorization: Bearer <token>
```

## API endpoints

### Public

- `GET /health` - health check
- `POST /auth/register` - register a candidate or company user
- `POST /auth/login` - authenticate and receive a JWT

### Protected

- `PATCH /candidate/update` - update the logged candidate profile
- `PATCH /company` - update the logged company profile
- `POST /company/job` - create a job for the logged company
- `GET /company/job` - list jobs for the logged company

## Project structure

- `src/main/java/com/vic/vagando/app` - domain, gateways, interactors
- `src/main/java/com/vic/vagando/infrastructure` - controllers, security, persistence, mappers, entities
- `src/main/resources/db/migration` - Flyway migrations

## Database

The project uses PostgreSQL and Flyway migrations.

Main tables created by migration:

- `users`
- `skills`
- `candidates`
- `candidate_skills`
- `companies`
- `jobs`
- `job_skills`
- `applications`

## Configuration

### Development profile

The `development` profile uses a local PostgreSQL database:

- URL: `jdbc:postgresql://localhost:5432/vagando`
- Username: `admin`
- Password: `123`
- JWT secret: `mysecretkey`

### Default profile

`application.yaml` expects environment variables:

- `DATABASE_URL`
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`
- `JWT_SECRET`

The active profile defaults to `development` when `SPRING_PROFILES_ACTIVE` is not set.

## Run locally on Windows

### 1. Start PostgreSQL

Make sure PostgreSQL is running and a database named `vagando` exists.

### 2. Run the application

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=development
```

Or with an environment variable:

```powershell
$env:SPRING_PROFILES_ACTIVE='development'; .\mvnw.cmd spring-boot:run
```

### 3. Access the API

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI docs: `http://localhost:8080/v3/api-docs`

## Example flow

1. Register a user with `POST /auth/register`
2. Login with `POST /auth/login`
3. Copy the returned token
4. Call protected endpoints with `Authorization: Bearer <token>`

## Notes

- The project uses DTOs, interactors, gateways, and mappers to keep the domain layer independent from persistence.
- Some entity relationships are bidirectional, so `toString`, `equals`, and `hashCode` must avoid recursive fields.

