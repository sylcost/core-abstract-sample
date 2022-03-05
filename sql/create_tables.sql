CREATE TABLE IF NOT EXISTS shoe (
  shoe_id INT NOT NULL,
  color varchar(250) NOT NULL,
  size INT NOT NULL,
  quantity INT NOT NULL DEFAULT 0,
  PRIMARY KEY (shoe_id)
);

