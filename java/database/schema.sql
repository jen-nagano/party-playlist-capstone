BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS events;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE event (
	event_id SERIAL,
	name text NOT NULL,
	date DATE NOT NULL,
	start_time TIMESTAMPTZ,
	end_time TIMESTAMPTZ,
	creator INTEGER,
	CONSTRAINT PK_event PRIMARY KEY (event_id),
	CONSTRAINT fk_creator FOREIGN KEY (creator) REFERENCES users(user_id)
);

COMMIT TRANSACTION;
