INSERT INTO account (id, balance)
VALUES ('A:Planet Express', 0),
       ('A:Le Palm D''Orbit', 0),
       ('A:Fishy Joe''s', 0),
       ('A:Elzar''s Fine Cuisine', 0),
       ('A:O''Zorgnax''s Pub', 0),
       ('A:Family Bros. Pizza', 0),
       ('A:O''Grady''s Pub', 0);

INSERT INTO restaurant (id, account_id)
VALUES ('R:Le Palm D''Orbit', 'A:Le Palm D''Orbit'),
       ('R:Fishy Joe''s', 'A:Fishy Joe''s'),
       ('R:Elzar''s Fine Cuisine', 'A:Elzar''s Fine Cuisine'),
       ('R:O''Zorgnax''s Pub', 'A:O''Zorgnax''s Pub'),
       ('R:Family Bros. Pizza', 'A:Family Bros. Pizza'),
       ('R:O''Grady''s Pub', 'A:O''Grady''s Pub');

INSERT INTO company (id, account_id, daily_allowance_limit)
VALUES ('C:Planet Express', 'A:Planet Express', 10);

INSERT INTO employee (id, company_id)
VALUES ('E:Fry', 'C:Planet Express'),
       ('E:Leela', 'C:Planet Express'),
       ('E:Bender', 'C:Planet Express'),
       ('E:Amy', 'C:Planet Express'),
       ('E:Hermes', 'C:Planet Express'),
       ('E:Professor', 'C:Planet Express'),
       ('E:Zoidberg', 'C:Planet Express'),
       ('E:Scruffy', 'C:Planet Express');