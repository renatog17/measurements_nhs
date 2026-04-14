CREATE TABLE measurements (
                              id BIGSERIAL PRIMARY KEY,

                              meter_property_id BIGINT NOT NULL,

                              reader_id BIGINT NOT NULL,

                              invoice_id BIGINT,

                              source VARCHAR(30) NOT NULL,

                              value NUMERIC(12,3) NOT NULL,

                              measured_at TIMESTAMP NOT NULL,

                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT fk_measurement_meter_property
                                  FOREIGN KEY (meter_property_id)
                                      REFERENCES meter_properties (id),

                              CONSTRAINT fk_measurement_reader
                                  FOREIGN KEY (reader_id)
                                      REFERENCES readers (id),

                              CONSTRAINT fk_measurement_invoice
                                  FOREIGN KEY (invoice_id)
                                      REFERENCES invoices (id)
);

CREATE INDEX idx_measurements_meter_property_date
    ON measurements (meter_property_id, measured_at DESC);

CREATE INDEX idx_measurements_reader
    ON measurements (reader_id);

CREATE UNIQUE INDEX uk_measurement_unique_read
    ON measurements (meter_property_id, measured_at);