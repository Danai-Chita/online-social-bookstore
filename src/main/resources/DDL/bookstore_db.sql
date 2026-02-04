DROP DATABASE bookstore_db;
CREATE DATABASE bookstore_db;
USE bookstore_db;

CREATE TABLE users(
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(255) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `role` ENUM('USER') NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE user_profiles (
    `profile_id` int PRIMARY KEY AUTO_INCREMENT,
    `user_id` int,
    `full_name` VARCHAR(255) NULL,
    `address` VARCHAR(255) NULL,
    `age` int NULL,
    `phone_number` VARCHAR(255),
    CONSTRAINT `user_fk_profile` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE book_categories (
    `category_id` int AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(100) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE books(
    `book_id` int PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `summary` VARCHAR(255) NOT NULL,
    `owner_id` int,
    `book_category_id` int,
    CONSTRAINT `owner_fk_book` FOREIGN KEY (`owner_id`) REFERENCES `user_profiles` (`profile_id`) ON DELETE CASCADE,
    CONSTRAINT `book_fk_category` FOREIGN KEY (`book_category_id`) REFERENCES `book_categories` (`category_id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE book_authors (
    `author_id` int AUTO_INCREMENT PRIMARY KEY,
    `author_name` VARCHAR(100) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE user_requested_books(
    `profile_id` int,
    `book_id` int,
    PRIMARY KEY (`profile_id`,`book_id`),
    KEY `book_id` (`book_id`),
    CONSTRAINT `user_req_fk_1` FOREIGN KEY (`profile_id`) REFERENCES `user_profiles` (`profile_id`),
    CONSTRAINT `user_req_fk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE favourite_book_authors(
    `profile_id` int,
    `author_id` int,
    PRIMARY KEY (`profile_id`,`author_id`),
    KEY `author_id` (`author_id`),
    CONSTRAINT `user_fav_fk_1` FOREIGN KEY (`profile_id`) REFERENCES `user_profiles` (`profile_id`),
    CONSTRAINT `user_fav_fk_2` FOREIGN KEY (`author_id`) REFERENCES `book_authors` (`author_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE book_authors_books(
    `book_id` int,
    `author_id` int,
    PRIMARY KEY (`book_id`,`author_id`),
    KEY `author_id` (`author_id`),
    CONSTRAINT `book_auth_fk_1` FOREIGN KEY (`author_id`) REFERENCES `book_authors` (`author_id`),
    CONSTRAINT `book_auth_fk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE user_favourite_categories(
    `profile_id` int,
    `category_id` int,
    PRIMARY KEY (`profile_id`,`category_id`),
    KEY `category_id` (`category_id`),
    CONSTRAINT `user_fav_cat_fk_1` FOREIGN KEY (`profile_id`) REFERENCES `user_profiles` (`profile_id`),
    CONSTRAINT `user_fav_cat_fk_2` FOREIGN KEY (`category_id`) REFERENCES `book_categories` (`category_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO book_categories(category_id,category_name) VALUES (1,'Art');
INSERT INTO book_categories(category_id,category_name) VALUES (2,'Comic');
INSERT INTO book_categories(category_id,category_name) VALUES (3,'Fantasy');
INSERT INTO book_categories(category_id,category_name) VALUES (4,'Fiction');
INSERT INTO book_categories(category_id,category_name) VALUES (5,'Biographies');
INSERT INTO book_categories(category_id,category_name) VALUES (6,'Historical');
INSERT INTO book_categories(category_id,category_name) VALUES (7,'Science');
INSERT INTO book_categories(category_id,category_name) VALUES (8,'Literature');
INSERT INTO book_categories(category_id,category_name) VALUES (9,'Adventure');
INSERT INTO book_categories(category_id,category_name) VALUES (10,'Crime');
INSERT INTO book_categories(category_id,category_name) VALUES (11,'Other');
