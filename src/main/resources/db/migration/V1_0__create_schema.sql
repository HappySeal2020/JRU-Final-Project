/*
  Create DB and tables for H2
*/
CREATE SCHEMA IF NOT EXISTS project;
use project;
DROP TABLE if exists author_to_book;
DROP TABLE if exists author;
DROP TABLE if exists book;
DROP TABLE if exists publisher;
DROP TABLE if exists user_tbl;

CREATE TABLE `project`.`user_tbl` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `login` VARCHAR(45) NOT NULL UNIQUE ,
                                      `email` VARCHAR(45) NOT NULL UNIQUE ,
                                      `password` VARCHAR(260) NOT NULL,
                                      `role` VARCHAR(10) NOT NULL,
                                      PRIMARY KEY (`id`));

CREATE TABLE `project`.`author` (
                                    `id` INT NOT NULL AUTO_INCREMENT,
                                    `name` VARCHAR(45) NOT NULL UNIQUE,
                                    PRIMARY KEY (`id`));

CREATE TABLE `project`.`publisher` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `name` VARCHAR(45) NOT NULL UNIQUE,
                                       `site` VARCHAR(45) NULL,
                                       PRIMARY KEY (`id`));

CREATE TABLE `project`.`book` (
                                  `id` INT NOT NULL AUTO_INCREMENT,
                                  `name` VARCHAR(150) NOT NULL UNIQUE,
                                  `author_id` INT NULL,
                                  `print_year` INT NULL,
                                  `publisher_id` INT NULL,
                                  `bbk` VARCHAR(45) NULL,
                                  `isbn` VARCHAR(45) NULL,
                                  `pages` INT NULL,
                                  PRIMARY KEY (`id`));

CREATE TABLE `project`.`author_to_book` (
                                            `id` INT NOT NULL AUTO_INCREMENT UNIQUE ,
                                            `author_id` INT NULL,
                                            `book_id` INT NULL,
                                            PRIMARY KEY (`id`));

