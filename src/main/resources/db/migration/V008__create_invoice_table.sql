CREATE TABLE invoices (
                          id BIGSERIAL PRIMARY KEY,

                          meter_property_id BIGINT NOT NULL,

                          reference_month DATE NOT NULL,

                          total_value NUMERIC(12,3) NOT NULL DEFAULT 0,

                          status VARCHAR(20) NOT NULL DEFAULT 'OPEN',

                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          closed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_invoice_meter_property
                              FOREIGN KEY (meter_property_id)
                                  REFERENCES meter_properties(id)
);

CREATE UNIQUE INDEX uk_open_invoice
    ON invoices (meter_property_id)
    WHERE status = 'OPEN';