INSERT INTO account (id, balance)
VALUES ('planet-express', 0),
       ('momcorp', 0),
       ('family-bros-pizza', 0);

INSERT INTO restaurant (id, account_id)
VALUES ('slurm-dispensing-unit', 'momcorp'),
       ('coffee-maker-3000', 'family-bros-pizza');

INSERT INTO customer (id, account_id, allowance_limit)
VALUES ('fry', 'planet-express', 10),
       ('leela', 'planet-express', 10),
       ('bender', 'planet-express', 10),
       ('amy', 'planet-express', 10),
       ('hermes', 'planet-express', 10),
       ('professor', 'planet-express', 10),
       ('zoidberg', 'planet-express', 10),
       ('scruffy', 'planet-express', 10);