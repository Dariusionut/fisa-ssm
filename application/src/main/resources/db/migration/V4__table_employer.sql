CREATE SEQUENCE IF NOT EXISTS seq_employer;

CREATE TABLE IF NOT EXISTS employer
(
    id           BIGINT UNIQUE      NOT NULL DEFAULT NEXTVAL('seq_employer'),
    fk_induction BIGINT UNIQUE      NOT NULL,
    name         VARCHAR(45) UNIQUE NOT NULL,
    cui_cif      VARCHAR(15) UNIQUE NOT NULL,
    caen         VARCHAR(200),
--     version INTEGER            NOT NULL DEFAULT 0,

    CONSTRAINT employer_pk_id PRIMARY KEY (id),
    CONSTRAINT employer_fk_induction_fk FOREIGN KEY (fk_induction) REFERENCES induction (id)
);


