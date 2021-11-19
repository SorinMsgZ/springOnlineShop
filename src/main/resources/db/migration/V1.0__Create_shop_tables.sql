CREATE TABLE IF NOT EXISTS Product_Category(
id  INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255),
description VARCHAR(255)
);

CREATE TABLE Supplier(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255)
);

CREATE TABLE Customer(
id   INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(255),
last_name VARCHAR(255),
user_name VARCHAR(255),
password VARCHAR(255),
email_address VARCHAR(255)
);

CREATE TABLE Product(
id   INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255),
description VARCHAR(255),
price DECIMAL,
weight DOUBLE,
category INT NOT NULL REFERENCES Product_Category(id),
supplier INT NOT NULL REFERENCES Supplier(id),
image_url VARCHAR(255)
);

CREATE TABLE Address(
id   INT AUTO_INCREMENT PRIMARY KEY,
country VARCHAR(255) UNIQUE NOT NULL,
city VARCHAR(255) UNIQUE NOT NULL,
county VARCHAR(255) UNIQUE NOT NULL,
street_address VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Location (
id   INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255),
address INT NOT NULL REFERENCES Address(id)
);

CREATE TABLE Stock(
product INT  NOT NULL REFERENCES Product(id) ON DELETE CASCADE,
location INT NOT NULL REFERENCES Location(id),
quantity INT,
CONSTRAINT pk_product_location PRIMARY KEY (product,location)
);

CREATE TABLE Orders(
id   INT AUTO_INCREMENT PRIMARY KEY,
shipped_from INT NOT NULL REFERENCES Location(id),
customer INT NOT NULL REFERENCES Customer(id),
created_at DATETIME,
address INT NOT NULL REFERENCES Address(id)
);

CREATE TABLE Order_Detail(
orders INT NOT NULL REFERENCES Orders(id),
product INT NOT NULL REFERENCES Product(id) ON DELETE CASCADE,
quantity INT,
CONSTRAINT pk_order_product PRIMARY KEY (orders,product)
);

CREATE TABLE Revenue(
id INT  AUTO_INCREMENT PRIMARY KEY,
location INT REFERENCES Location(id),
local_date DATE,
sum DECIMAL
);