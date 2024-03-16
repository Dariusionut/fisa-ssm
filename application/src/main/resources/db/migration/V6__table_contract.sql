CREATE SEQUENCE seq_contract;

CREATE TABLE contract
(
    id         BIGINT UNIQUE NOT NULL DEFAULT NEXTVAL('seq_contract'),
    fk_job     INTEGER       NOT NULL,
--     fk_employer   INTEGER          NOT NULL,
    number     VARCHAR(45)   NOT NULL,
    fixed_term BOOLEAN       NOT NULL,
--     base_salary   DOUBLE PRECISION NOT NULL,
--     active_status BOOLEAN          NOT NULL,
    version    INTEGER       NOT NULL DEFAULT 0,

    CONSTRAINT contract_pk_id PRIMARY KEY (id)
--     CONSTRAINT contract_fk_job FOREIGN KEY (fk_job) REFERENCES job (id),
--     CONSTRAINT contract_fk_employer FOREIGN KEY (fk_employer) REFERENCES employer (id)
);
