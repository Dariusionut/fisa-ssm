CREATE SEQUENCE IF NOT EXISTS seq_employer;

CREATE TABLE IF NOT EXISTS employer(
    id INTEGER UNIQUE NOT NULL DEFAULT NEXTVAL('seq_employer'),
    name VARCHAR(45) UNIQUE NOT NULL,
    cui_cif VARCHAR(15) UNIQUE NOT NULL,
    caen_code VARCHAR(15),
    caen_description VARCHAR(100),
    address VARCHAR(100),
    email VARCHAR(100),

    CONSTRAINT employer_pk_id PRIMARY KEY (id)
);


