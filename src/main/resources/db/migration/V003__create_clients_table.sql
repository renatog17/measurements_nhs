CREATE TABLE clients (
                         id BIGSERIAL PRIMARY KEY,

                         name VARCHAR(120) NOT NULL,

                         document VARCHAR(20) NOT NULL UNIQUE,

                         person_type VARCHAR(20) NOT NULL,

                         email VARCHAR(150) NOT NULL UNIQUE,

                         active BOOLEAN NOT NULL DEFAULT TRUE,

                         address_id BIGINT,

                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_client_address
                             FOREIGN KEY (address_id)
                                 REFERENCES addresses(id)
);