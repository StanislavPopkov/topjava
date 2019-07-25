DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS global_seq2;

CREATE SEQUENCE global_seq2 START WITH 100000;

CREATE TABLE meals
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq2'),
  userid           INTEGER                 NOT NULL,
  datetime         TIMESTAMP               NOT NULL,
  description      VARCHAR                 NOT NULL,
  calories         INTEGER                 NOT NULL
);