CREATE SEQUENCE IF NOT EXISTS seq_nationality;

CREATE TABLE IF NOT EXISTS nationality
(
    id   INTEGER UNIQUE NOT NULL DEFAULT NEXTVAL('seq_nationality'),
    name VARCHAR(45)    NOT NULL,
    version INTEGER            NOT NULL DEFAULT 0,

    CONSTRAINT nationality_pk_id PRIMARY KEY (id)
);

INSERT INTO nationality (name)
VALUES ('România');
