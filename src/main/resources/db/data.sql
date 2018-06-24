INSERT INTO users(id, username, password, enabled) VALUES(1, 'max.a.ramos@gmail.com', 'changeit', TRUE);

INSERT INTO authorities(id, authority) VALUES(1, 'ROLE_ADMIN');
INSERT INTO authorities(id, authority) VALUES(2, 'ROLE_USER');

INSERT INTO users_authorities(user_id, authority_id) VALUES(1, 1);