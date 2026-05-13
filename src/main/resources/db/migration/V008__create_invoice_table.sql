CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    reference_month DATE NOT NULL,
    installation_id BIGINT NOT NULL,
    total_consumed_volume NUMERIC(12,3) DEFAULT 0,
    price_per_m3 NUMERIC(10,4) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    closed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount_due NUMERIC(10,2),
    volume_difference NUMERIC(10,2),
    CONSTRAINT fk_installation
        FOREIGN KEY (installation_id)
            REFERENCES installations (id)
);