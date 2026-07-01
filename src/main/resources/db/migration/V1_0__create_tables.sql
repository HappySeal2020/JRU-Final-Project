/*
  Create DB and tables for Postgres
*/

/*
DROP TABLE if exists author_to_book;
DROP TABLE if exists author;
DROP TABLE if exists book;
DROP TABLE if exists publisher;
DROP TABLE if exists user_tbl;
*/

CREATE TABLE user_tbl (
                                      id BIGSERIAL PRIMARY KEY NOT NULL,
                                      login VARCHAR(45) NOT NULL UNIQUE ,
                                      email VARCHAR(45) NOT NULL UNIQUE ,
                                      password VARCHAR(260) NOT NULL,
                                      role VARCHAR(10) NOT NULL);


CREATE TABLE author (
                                    id BIGSERIAL PRIMARY KEY NOT NULL,
                                    name VARCHAR(45) NOT NULL UNIQUE);


CREATE TABLE publisher (
                                       id BIGSERIAL PRIMARY KEY NOT NULL,
                                       name VARCHAR(45) NOT NULL UNIQUE,
                                       site VARCHAR(45) NULL);


CREATE TABLE book (
                                  id BIGSERIAL PRIMARY KEY NOT NULL,
                                  name VARCHAR(150) NOT NULL UNIQUE,
                                  author_id BIGINT NULL,
                                  print_year INT NULL,
                                  publisher_id BIGINT NULL REFERENCES publisher(id),
                                  bbk VARCHAR(45) NULL,
                                  isbn VARCHAR(45) NULL,
                                  pages INT NULL);


CREATE TABLE author_to_book (
                                            id BIGSERIAL PRIMARY KEY NOT NULL,
                                            author_id BIGINT NULL REFERENCES author(id),
                                            book_id BIGINT NULL REFERENCES book(id));

