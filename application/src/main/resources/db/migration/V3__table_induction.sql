CREATE SEQUENCE IF NOT EXISTS seq_induction;

CREATE TABLE IF NOT EXISTS induction
(
    id         BIGINT UNIQUE NOT NULL DEFAULT NEXTVAL('seq_induction'),
    value      TEXT          NOT NULL,
    updated_at TIMESTAMPTZ   NOT NULL DEFAULT NOW()::TIMESTAMPTZ,
    version    INTEGER       NOT NULL DEFAULT 0,

    CONSTRAINT induction_pk PRIMARY KEY (id)
)
