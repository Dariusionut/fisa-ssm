CREATE TABLE app_user_contract
(
    fk_app_user BIGINT UNIQUE NOT NULL,
    fk_contract BIGINT UNIQUE NOT NULL,

    CONSTRAINT app_user_contract_pk PRIMARY KEY (fk_app_user, fk_contract),
    CONSTRAINT app_user_contract_fk_app_user_fk FOREIGN KEY (fk_app_user) REFERENCES app_user (id),
    CONSTRAINT app_user_contract_fk_contract_fk FOREIGN KEY (fk_contract) REFERENCES contract (id)
);
