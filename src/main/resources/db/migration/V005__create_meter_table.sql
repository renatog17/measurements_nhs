CREATE TABLE meters (
                        id BIGSERIAL PRIMARY KEY,
                        serial_number VARCHAR(120) NOT NULL UNIQUE,
                        type VARCHAR(50),
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);