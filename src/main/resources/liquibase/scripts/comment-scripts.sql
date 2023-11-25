-- liquibase formatted sql

-- changeset amilenkov:1
CREATE TABLE comment
(

    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    text       VARCHAR(255),
    pk_ad      BIGINT,
    author_id  BIGINT,
    FOREIGN KEY (pk_ad) REFERENCES ad (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
);