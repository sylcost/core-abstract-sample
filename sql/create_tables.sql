CREATE TABLE IF NOT EXISTS shoe (
  shoe_id INT NOT NULL,
  color varchar(250) NOT NULL,
  size INT NOT NULL,
  quantity INT NOT NULL DEFAULT 0,
  PRIMARY KEY (shoe_id)
);

CREATE TABLE IF NOT EXISTS shop (
  shop_id INT NOT NULL,
  shop_name varchar(250),
  PRIMARY KEY (shop_id)
);

CREATE TABLE IF NOT EXISTS stock (
  shop_id INT NOT NULL REFERENCES shop (shop_id),
  shoe_id INT NOT NULL REFERENCES shoe (shoe_id),
  quantity INT NOT NULL DEFAULT 0,
  PRIMARY KEY (shop_id, shoe_id) 
);

