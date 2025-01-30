-- Create the database
CREATE DATABASE ovinplus;

-- Use the created database
USE ovinplus;

-- Create contact table
CREATE TABLE contact (
    contactId VARCHAR(20) PRIMARY KEY,
    date DATE,
    Student_Name VARCHAR(50),
    contactNumber INT NOT NULL
);

-- Create User table
CREATE TABLE User (
    userId VARCHAR(20),
    userName VARCHAR(50),
    contactId VARCHAR(20),
    FOREIGN KEY (contactId) REFERENCES contact(contactId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create Batch table
CREATE TABLE Batch (
    BatchId VARCHAR(20) PRIMARY KEY,
    BatchName VARCHAR(100),
    Student_count int
);

-- Create Student table
CREATE TABLE Student (
    StudentId VARCHAR(20) PRIMARY KEY,
    StudentName VARCHAR(50),
    address VARCHAR(100) NOT NULL,
    BatchId VARCHAR(20),
    FOREIGN KEY (BatchId) REFERENCES Batch(BatchId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create Order table
CREATE TABLE Orders (
    orderId VARCHAR(20) PRIMARY KEY,
    StudentId VARCHAR(20),
    FOREIGN KEY (StudentId) REFERENCES Student(StudentId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create Contact_Student_Detail table
CREATE TABLE Contact_Student_Detail (
    StudentId VARCHAR(20),
    FOREIGN KEY (StudentId) REFERENCES Student(StudentId)
    ON UPDATE CASCADE ON DELETE CASCADE,
    orderId VARCHAR(20),
    FOREIGN KEY (orderId) REFERENCES `Order`(orderId)
    ON UPDATE CASCADE ON DELETE CASCADE,
    status VARCHAR(20)
);

-- Create Delivery table
CREATE TABLE Delivery (
    DeliveryId VARCHAR(20) PRIMARY KEY,
    Date DATE,
    Status VARCHAR(100),
    orderId VARCHAR(20),
    FOREIGN KEY (orderId) REFERENCES `Order`(orderId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create Payment table
CREATE TABLE Payment (
    PaymentId VARCHAR(20) PRIMARY KEY,
    Date DATE,
    Amount DECIMAL(10, 2),
    DeliveryId VARCHAR(20),
    FOREIGN KEY (DeliveryId) REFERENCES Delivery(DeliveryId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create Employee table
CREATE TABLE Employee (
    EmployeeId VARCHAR(20) PRIMARY KEY,
    Name VARCHAR(100),
    joinDate DATE,
    jobRole VARCHAR(50)
);
