CREATE TABLE IF NOT EXISTS app_role
(
    id   SMALLINT UNIQUE NOT NULL,
    name VARCHAR(15),

    CONSTRAINT app_role_pk_id PRIMARY KEY (id)
);

INSERT INTO app_role(id, name)
VALUES (1, 'EMPLOYEE'),
       (2, 'ADMIN');


