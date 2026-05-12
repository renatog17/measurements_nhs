CREATE TABLE clients (
                         id BIGSERIAL PRIMARY KEY,

                         name VARCHAR(120) NOT NULL,

                         cpf VARCHAR(11) NOT NULL UNIQUE,

                         email VARCHAR(150) NOT NULL UNIQUE,

                         active BOOLEAN NOT NULL DEFAULT TRUE,

                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);