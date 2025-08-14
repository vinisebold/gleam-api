create database Gleam
use Gleam

CREATE TABLE Gleam (
    id INT NOT NULL AUTO_INCREMENT,
    nm_produtos VARCHAR(255) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
);


SELECT * FROM Gleam;
