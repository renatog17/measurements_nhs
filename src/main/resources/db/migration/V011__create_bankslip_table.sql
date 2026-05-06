CREATE TABLE bank_slip (
                           id BIGSERIAL PRIMARY KEY,

                           nosso_numero VARCHAR(100) NOT NULL UNIQUE,
                           digitable_line VARCHAR(255) NOT NULL,
                           barcode VARCHAR(255) NOT NULL,

                           amount NUMERIC(19, 2) NOT NULL,
                           due_date DATE NOT NULL,

                           status VARCHAR(50) NOT NULL,
                           active BOOLEAN NOT NULL DEFAULT TRUE,

                           invoice_id BIGINT NOT NULL,

                           CONSTRAINT fk_bank_slip_invoice
                               FOREIGN KEY (invoice_id)
                                   REFERENCES invoices(id)
);

CREATE INDEX idx_bank_slip_invoice_id ON bank_slip(invoice_id);
CREATE INDEX idx_bank_slip_active ON bank_slip(active);
CREATE INDEX idx_bank_slip_invoice_active
    ON bank_slip(invoice_id, active);