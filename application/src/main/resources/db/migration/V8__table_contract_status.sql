CREATE TABLE IF NOT EXISTS contract_status
(
    id   SMALLINT UNIQUE    NOT NULL,
    name VARCHAR(15) UNIQUE NOT NULL,

    CONSTRAINT contract_stauts_pk_id PRIMARY KEY (id)
);

INSERT INTO contract_status (id, name)
VALUES (1, 'ACTIVE'),
       (2, 'INACTIVE'),
       (3, 'SUSPENDED')
ON CONFLICT DO NOTHING;
