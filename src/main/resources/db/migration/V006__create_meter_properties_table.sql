CREATE TABLE meter_properties (
                                  id BIGSERIAL PRIMARY KEY,

                                  meter_id BIGINT NOT NULL,
                                  property_id BIGINT NOT NULL,

                                  active BOOLEAN NOT NULL DEFAULT TRUE,

                                  assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  unassigned_at TIMESTAMP,

                                  CONSTRAINT fk_meter FOREIGN KEY (meter_id) REFERENCES meters(id),
                                  CONSTRAINT fk_property FOREIGN KEY (property_id) REFERENCES properties(id)
);

CREATE UNIQUE INDEX uk_one_active_meter_per_property
    ON meter_properties (property_id)
    WHERE active = true;