CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('CANDIDATE', 'COMPANY', 'ADMIN')),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS skills (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS candidates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_name VARCHAR(150) NOT NULL,
    description TEXT,
    user_id UUID NOT NULL UNIQUE,
    resume_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    cpf VARCHAR(14) UNIQUE,
    CONSTRAINT fk_candidate_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS candidate_skills (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_id UUID NOT NULL,
    skill_id UUID NOT NULL,
    FOREIGN KEY (candidate_id)
    REFERENCES candidates(id)
    ON DELETE CASCADE,
    CONSTRAINT fk_candidate_skill_skill
    FOREIGN KEY (skill_id)
    REFERENCES skills(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS companies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_name VARCHAR(150) NOT NULL,
    description TEXT,
    user_id UUID NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    cnpj VARCHAR(18) UNIQUE,
    CONSTRAINT fk_company_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS jobs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id UUID NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_job_company
    FOREIGN KEY (company_id)
    REFERENCES companies(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS job_skills (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL,
    skill_id UUID NOT NULL,
    FOREIGN KEY (job_id)
    REFERENCES jobs(id)
    ON DELETE CASCADE,
    CONSTRAINT fk_job_skill_skill
    FOREIGN KEY (skill_id)
    REFERENCES skills(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS applications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_id UUID NOT NULL,
    job_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
    CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    score NUMERIC(5,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_application_candidate
    FOREIGN KEY (candidate_id)
    REFERENCES candidates(id)
    ON DELETE CASCADE,
    CONSTRAINT fk_application_job
    FOREIGN KEY (job_id)
    REFERENCES jobs(id)
    ON DELETE CASCADE,
    CONSTRAINT unique_application
    UNIQUE (candidate_id, job_id)
    );