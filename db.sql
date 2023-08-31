DROP DATABASE IF EXISTS SAS2;
CREATE DATABASE IF NOT EXISTS SAS2;

USE SAS2;

CREATE TABLE Borrower(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(50) NOT NULL
);

CREATE TABLE Book(
    isbn VARCHAR(50) PRIMARY KEY,
    author VARCHAR(50) NOT NULL,
    title VARCHAR(50) NOT NULL,
    status ENUM('BORROWED', 'AVAILABLE', 'LOST'),
    borrower int
);

CREATE TABLE Borrower_Book(
    isbn varchar(50),
    idBorrower int,
    borrow_start DATE NOT NULL,
    borrow_end DATE NOT NULL,
    PRIMARY KEY(isbn, idBorrower),
    FOREIGN KEY (idBorrower) REFERENCES `Borrower`(id),
    FOREIGN KEY (isbn) REFERENCES `Book`(isbn)
);