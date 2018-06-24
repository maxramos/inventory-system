INSERT INTO users(username, password) VALUES('max.a.ramos@gmail.com', 'changeit');

INSERT INTO roles(authority) VALUES('ROLE_USER');

INSERT INTO users_roles(username, authority) VALUES('max.a.ramos@gmail.com', 'ROLE_USER');