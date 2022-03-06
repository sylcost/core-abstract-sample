CREATE TABLE IF NOT EXISTS shoe (
  id INT NOT NULL,
  name varchar(250) NOT NULL,
  color varchar(250) NOT NULL,
  size INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shop (
  id INT NOT NULL,
  name varchar(250),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS stock (
  shop_id INT NOT NULL REFERENCES shop (id),
  shoe_id INT NOT NULL REFERENCES shoe (id),
  quantity INT NOT NULL DEFAULT 0,
  PRIMARY KEY (shop_id, shoe_id) 
);

