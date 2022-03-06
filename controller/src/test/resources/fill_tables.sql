INSERT INTO SHOE (id, color, size, name) VALUES (1, 'BLACK', 40, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (2, 'BLACK', 41, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (3, 'BLACK', 42, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (4, 'BLACK', 43, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (5, 'BLUE', 40, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (6, 'BLUE', 41, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (7, 'BLUE', 42, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (8, 'BLUE', 43, 'shoe1');
INSERT INTO SHOE (id, color, size, name) VALUES (9, 'RED', 40, 'shoe2');
INSERT INTO SHOE (id, color, size, name) VALUES (10, 'RED', 41, 'shoe2');
INSERT INTO SHOE (id, color, size, name) VALUES (11, 'RED', 42, 'shoe2');
INSERT INTO SHOE (id, color, size, name) VALUES (12, 'RED', 43, 'shoe2');

INSERT INTO SHOP (id, name) VALUES (1, 'DECATHLON NANTES');
INSERT INTO SHOP (id, name) VALUES (2, 'DECATHLON LILLE');

INSERT INTO STOCK (shop_id, shoe_id, quantity) VALUES (1, 1, 10);
INSERT INTO STOCK (shop_id, shoe_id, quantity) VALUES (1, 5, 5);
INSERT INTO STOCK (shop_id, shoe_id, quantity) VALUES (1, 11, 9);
INSERT INTO STOCK (shop_id, shoe_id, quantity) VALUES (2, 2, 1);
INSERT INTO STOCK (shop_id, shoe_id, quantity) VALUES (2, 6, 0);
INSERT INTO STOCK (shop_id, shoe_id, quantity) VALUES (2, 8, 2);
