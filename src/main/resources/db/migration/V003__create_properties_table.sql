CREATE TABLE properties (
                            id BIGSERIAL PRIMARY KEY,

                            address VARCHAR(255),

                            city VARCHAR(120),

                            identifier_code VARCHAR(120) UNIQUE,

                            active BOOLEAN NOT NULL DEFAULT TRUE,

                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);