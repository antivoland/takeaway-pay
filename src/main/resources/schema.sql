CREATE TABLE account
(
    id      VARCHAR(256) PRIMARY KEY NOT NULL,
    balance DOUBLE                   NOT NULL,
    version INT                      NOT NULL DEFAULT 0
);

CREATE TABLE restaurant
(
    id         VARCHAR(256) PRIMARY KEY NOT NULL,
    account_id VARCHAR(256)             NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE customer
(
    id         VARCHAR(256) PRIMARY KEY NOT NULL,
    account_id VARCHAR(256)             NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id)
);