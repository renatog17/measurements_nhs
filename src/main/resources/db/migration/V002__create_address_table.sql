CREATE TABLE addresses (

                           id BIGSERIAL PRIMARY KEY,

                           street VARCHAR(255) NOT NULL,

                           number VARCHAR(50),

                           complement VARCHAR(255),

                           neighborhood VARCHAR(120) NOT NULL,

                           city VARCHAR(120) NOT NULL,

                           state VARCHAR(2) NOT NULL,

                           zip_code VARCHAR(9),

                           active BOOLEAN NOT NULL DEFAULT TRUE,

                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

);