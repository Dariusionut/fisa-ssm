CREATE SEQUENCE IF NOT EXISTS seq_app_user;

CREATE TABLE IF NOT EXISTS app_user
(
    id             BIGINT UNIQUE       NOT NULL DEFAULT NEXTVAL('seq_app_user'),
    fk_role        SMALLINT            NOT NULL DEFAULT 1,
    fk_nationality INTEGER             NOT NULL DEFAULT 1,
    fk_contract    BIGINT UNIQUE       NULL,
    first_name     VARCHAR(45)         NOT NULL,
    last_name      VARCHAR(45)         NOT NULL,
    date_of_birth  DATE,
    cnp            VARCHAR(13) UNIQUE  NOT NULL,
    password       VARCHAR(100),
    email          VARCHAR(100) UNIQUE NULL,
    address        VARCHAR(100)        NULL,
    created_at     TIMESTAMPTZ         NOT NULL DEFAULT NOW()::TIMESTAMPTZ,
    updated_at     TIMESTAMPTZ         NULL,
    has_errors     BOOLEAN             NOT NULL DEFAULT FALSE,
    version        INTEGER             NOT NULL DEFAULT 0,

    CONSTRAINT app_user_pk_id PRIMARY KEY (id),
    CONSTRAINT app_user_fk_role FOREIGN KEY (fk_role) REFERENCES app_role (id),
    CONSTRAINT app_user_fk_nationality FOREIGN KEY (fk_nationality) REFERENCES nationality (id),
    CONSTRAINT app_user_fk_contract FOREIGN KEY (fk_contract) REFERENCES contract (id)
);



