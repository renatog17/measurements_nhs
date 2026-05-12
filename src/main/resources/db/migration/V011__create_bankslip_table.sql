CREATE TABLE bank_slips (
                            id BIGSERIAL PRIMARY KEY,

                            nosso_numero VARCHAR(100) NOT NULL UNIQUE,
                            digitable_line VARCHAR(255) NOT NULL,
                            barcode VARCHAR(255) NOT NULL,

                            amount NUMERIC(19, 2) NOT NULL,
                            due_date DATE NOT NULL,

                            status VARCHAR(50) NOT NULL,

                            active BOOLEAN NOT NULL DEFAULT TRUE,
                            paid BOOLEAN NOT NULL DEFAULT FALSE,
                            paid_at TIMESTAMP,

                            invoice_id BIGINT NOT NULL,

                            CONSTRAINT fk_bank_slip_invoice
                                FOREIGN KEY (invoice_id)
                                    REFERENCES invoices(id)
);

CREATE INDEX idx_bank_slips_invoice_id
    ON bank_slips(invoice_id);

CREATE INDEX idx_bank_slips_active
    ON bank_slips(active);

CREATE INDEX idx_bank_slips_invoice_active
    ON bank_slips(invoice_id, active);

CREATE INDEX idx_bank_slips_status
    ON bank_slips(status);