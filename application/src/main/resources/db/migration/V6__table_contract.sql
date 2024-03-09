CREATE SEQUENCE seq_contract;

CREATE TABLE contract
(
    id            BIGINT UNIQUE    NOT NULL DEFAULT NEXTVAL('seq_contract'),
    fk_cor        INTEGER          NOT NULL,
    fk_employer   INTEGER          NOT NULL,
    number        VARCHAR(45)      NOT NULL,
    fixed_term    BOOLEAN          NOT NULL,
    base_salary   DOUBLE PRECISION NOT NULL,
    active_status BOOLEAN          NOT NULL,

    CONSTRAINT contract_pk_id PRIMARY KEY (id),
    CONSTRAINT contract_fk_cor FOREIGN KEY (fk_cor) REFERENCES cor (id),
    CONSTRAINT contract_fk_employer FOREIGN KEY (fk_employer) REFERENCES employer (id)
);
