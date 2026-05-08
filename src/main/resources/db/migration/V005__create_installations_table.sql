CREATE TABLE installations (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    meter_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    volume_at_assigned NUMERIC(12,3) NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unassigned_at TIMESTAMP,

    CONSTRAINT fk_client
        FOREIGN KEY (client_id)
            REFERENCES clients (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_property
        FOREIGN KEY (property_id)
            REFERENCES properties (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_meter
        FOREIGN KEY (meter_id)
            REFERENCES meters (id)
            ON DELETE CASCADE
);

