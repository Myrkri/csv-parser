CREATE TABLE user_details
(
    id       BIGINT       AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles    VARCHAR(255),
    enabled  BOOLEAN,
    CONSTRAINT pk_user PRIMARY KEY (id)
);