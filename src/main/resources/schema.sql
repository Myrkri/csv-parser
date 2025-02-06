CREATE TABLE user_details
(
    id       BIGINT       AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles    VARCHAR(255),
    enabled  BOOLEAN,
    CONSTRAINT pk_user PRIMARY KEY (id)
);
CREATE TABLE upload_status
(
    id       BIGINT       AUTO_INCREMENT NOT NULL,
    process_id VARCHAR(MAX) NOT NULL,
    processed_records NUMERIC NOT NULL,
    status VARCHAR(60) NOT NULL,
    CONSTRAINT pk_upload_status PRIMARY KEY (id)
);