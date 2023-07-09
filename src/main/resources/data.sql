INSERT INTO account (id, balance)
VALUES ('momcorp-account', 0),
       ('family-bros-pizza-account', 0),
       ('fry-account', 10),
       ('leela-account', 10),
       ('bender-account', 10),
       ('amy-account', 10),
       ('hermes-account', 10),
       ('professor-account', 10),
       ('zoidberg-account', 10),
       ('scruffy-account', 10);

INSERT INTO restaurant (id, account_id)
VALUES ('slurm-dispensing-unit', 'momcorp-account'),
       ('coffee-maker-3000', 'family-bros-pizza-account');

INSERT INTO customer (id, account_id)
VALUES ('fry', 'fry-account'),
       ('leela', 'leela-account'),
       ('bender', 'bender-account'),
       ('amy', 'amy-account'),
       ('hermes', 'hermes-account'),
       ('professor', 'professor-account'),
       ('zoidberg', 'zoidberg-account'),
       ('scruffy', 'scruffy-account');