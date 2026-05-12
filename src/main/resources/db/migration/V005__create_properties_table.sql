CREATE TABLE properties (
    id BIGSERIAL PRIMARY KEY,
    address_id BIGINT,
    name VARCHAR(120),
    identifier_code VARCHAR(120) UNIQUE,
    type VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    parent_property_id BIGINT,

    CONSTRAINT fk_property_parent
        FOREIGN KEY (parent_property_id)
            REFERENCES properties(id),

    CONSTRAINT fk_property_address
        FOREIGN KEY (address_id)
            REFERENCES addresses(id)
);