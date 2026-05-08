CREATE TABLE invoices (
                          id BIGSERIAL PRIMARY KEY,

                          meter_id BIGINT NOT NULL,

                          reference_month DATE NOT NULL,

                          meter_property_id BIGINT NOT NULL,

                          total_consumed_volume NUMERIC(12,3) NOT NULL DEFAULT 0,

                          price_per_m3 NUMERIC(10,4) NOT NULL,

                          status VARCHAR(20) NOT NULL DEFAULT 'OPEN',

                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          closed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_measurement_meter
                              FOREIGN KEY (meter_property_id)
                                  REFERENCES meter_properties (id),

                          CONSTRAINT fk_invoice_meter
                              FOREIGN KEY (meter_id)
                                  REFERENCES meters(id)
);

CREATE UNIQUE INDEX uk_open_invoice
    ON invoices (meter_id)
    WHERE status = 'OPEN';