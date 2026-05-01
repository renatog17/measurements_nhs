CREATE TABLE meters (
                        id BIGSERIAL PRIMARY KEY,
                        serial_number VARCHAR(120) NOT NULL UNIQUE,
                        type VARCHAR(50),
                        value NUMERIC(12,3) NOT NULL,
                        max_value NUMERIC(12,3) NOT NULL,
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        reset INT DEFAULT 0,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);