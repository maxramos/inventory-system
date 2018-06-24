CREATE TABLE users (
	username VARCHAR(30) PRIMARY KEY,
	password VARCHAR(30)
);

CREATE TABLE roles (
	authority VARCHAR(20) PRIMARY KEY
);

CREATE TABLE users_roles (
	username VARCHAR(30) REFERENCES users(username),
	authority VARCHAR(20) REFERENCES roles(authority),
	PRIMARY KEY(username, authority)
);
