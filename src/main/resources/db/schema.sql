CREATE TABLE users (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL UNIQUE,
	password VARCHAR(60) NOT NULL DEFAULT '',
	enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE authorities (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	authority VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE users_authorities (
	user_id BIGINT REFERENCES users(id),
	authority_id BIGINT REFERENCES authorities(id),
	PRIMARY KEY(user_id, authority_id)
);
