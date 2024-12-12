DROP TABLE IF EXISTS NSUser;

CREATE TABLE NSUSER (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              name VARCHAR(250) NOT NULL);

INSERT INTO NSUser (name) VALUES
('Aliko'),
('Bill'),
('John'),
('Jane'),
('Patel'),
('Kumar'),
('Cathy');
