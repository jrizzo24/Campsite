CREATE TABLE reservations (
	id        SERIAL PRIMARY KEY,
	full_name  TEXT,
	email     TEXT
);

CREATE INDEX id_idx ON reservations(id);

CREATE TABLE reserved_days (
	day            DATE,
	reservation_id  INT REFERENCES reservations
);

CREATE INDEX res_id_idx ON reserved_days(reservation_id);
CREATE UNIQUE INDEX day_idx ON reserved_days(day);

CREATE SEQUENCE hibernate_sequence START 1;