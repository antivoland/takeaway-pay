CREATE TABLE account
(
    id      VARCHAR(256) PRIMARY KEY NOT NULL,
    balance DOUBLE                   NOT NULL
);

CREATE TABLE restaurant
(
    id         VARCHAR(256) PRIMARY KEY NOT NULL,
    account_id VARCHAR(256)             NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE company
(
    id                    VARCHAR(256) PRIMARY KEY NOT NULL,
    account_id            VARCHAR(256)             NOT NULL,
    daily_allowance_limit DOUBLE                   NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE employee
(
    id         VARCHAR(256) PRIMARY KEY NOT NULL,
    company_id VARCHAR(256)             NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company (id)
);