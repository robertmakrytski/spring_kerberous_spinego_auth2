INSERT IGNORE INTO testsecurity.`user`
(id, email, firstname, lastname, password, `role`, username)
VALUES(1, 'mail@ru', 'test', 'test', 'password', 'ROLE_ADMIN', 'test1');
INSERT IGNORE INTO testsecurity.`user`
(id, email, firstname, lastname, password, `role`, username)
VALUES(2, 't@ru', 'tset', 'test', 'test', 'ROLE_ADMIN', 'cadmin@GDEV.TEST');
INSERT IGNORE INTO testsecurity.`user`
(id, email, firstname, lastname, password, `role`, username)
VALUES(5, 't@ru', 'tset', 'test', '{noop}password', 'ROLE_ADMIN', 'aaaa');
INSERT IGNORE INTO testsecurity.`user`
(id, email, firstname, lastname, password, `role`, username)
VALUES(7, 'd@ru', 'tset', 'tset', 'password', 'ROLE_ADMIN', 'user1');