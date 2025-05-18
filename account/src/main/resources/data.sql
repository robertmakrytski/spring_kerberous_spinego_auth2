INSERT IGNORE INTO testsecurity.`user`
(id, email, firstname, lastname, password, `role`, username)
VALUES(1, 'mail@ru', 'test', 'test', '{noop}password', 'ROLE_ADMIN', 'test1');