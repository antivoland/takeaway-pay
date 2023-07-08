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

CREATE TABLE customer
(
    id              VARCHAR(256) PRIMARY KEY NOT NULL,
    account_id      VARCHAR(256)             NOT NULL,
    allowance_limit DOUBLE                   NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id)
);


CREATE TABLE payment
(
    id IDENTITY PRIMARY KEY NOT NULL,
    customer_id VARCHAR(256) NOT NULL,
    amount      DOUBLE       NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);