CREATE TABLE whitelist (

                           id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           email VARCHAR(255) NOT NULL

);

CREATE TABLE admin (

                       email_id INT NOT NULL,
                       FOREIGN KEY (email_id) REFERENCES whitelist(id)

);

CREATE TABLE users (

                       email_id INT NOT NULL,
                       pseudo VARCHAR(255)NOT NULL,
                       password VARCHAR(255)NOT NULL,
                       role VARCHAR(15)NOT NULL,
                       FOREIGN KEY (email_id) REFERENCES whitelist(id)

);

CREATE TABLE store (

                       id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL

);

CREATE TABLE user_store (

                            email_id INT NOT NULL,
                            store_id INT NOT NULL,
                            FOREIGN KEY (email_id) REFERENCES whitelist(id),
                            FOREIGN KEY (store_id) REFERENCES store(id)

);

CREATE TABLE product (

                         id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         price INT NOT NULL

);

CREATE TABLE product_in_store (

                                  product_id INT NOT NULL,
                                  store_id INT NOT NULL,
                                  quantity INT NOT NULL,
                                  FOREIGN KEY (product_id) REFERENCES product(id),
                                  FOREIGN KEY (store_id) REFERENCES store(id)

);

INSERT INTO whitelist VALUES ("admin@admin.com");


