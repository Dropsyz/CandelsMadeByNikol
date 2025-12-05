CREATE TABLE categories
(
    id          BINARY(16)   NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description CLOB,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

ALTER TABLE categories
    ADD CONSTRAINT uc_categories_name UNIQUE (name);