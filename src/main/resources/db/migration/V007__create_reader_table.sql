CREATE TABLE readers (
                         id BIGSERIAL PRIMARY KEY,

                         name VARCHAR(150) NOT NULL,

                         employee_code VARCHAR(100) NOT NULL UNIQUE,

                         active BOOLEAN NOT NULL DEFAULT TRUE,

                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_readers_active
    ON readers (active);