DROP TABLE IF EXISTS NSUSER;

CREATE TABLE NSUSER (
    user_id BIGINT AUTO_INCREMENT  PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    user_status VARCHAR(1) NOT NULL,
    department VARCHAR(255) DEFAULT ON NULL
);

INSERT INTO NSUSER VALUES
(123, 'u123', 'fname1', 'lname1', 'email1@test.com', 'I', null),
(456, 'u456', 'fname2', 'lname2', 'email2@test.com', 'A', null),
(789, 'u789', 'fname3', 'lname3', 'email3@test.com', 'T', null)
