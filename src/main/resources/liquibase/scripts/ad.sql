-- liquibase formatted sql

-- changeset belchikdm:1
CREATE TABLE ad
(

    id          BIGSERIAL PRIMARY KEY,
    description varchar(255),
    price       INT,
    title       VARCHAR(255),
    image       VARCHAR(255),
    author_id   BIGINT REFERENCES users(id)

);