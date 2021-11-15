CREATE TABLE ProductCategory(
id INT PRIMARY KEY,
name TEXT,
description TEXT);

CREATE TABLE Supplier(
id INT PRIMARY KEY,
name TEXT);

CREATE TABLE Customer(
id INT PRIMARY KEY,
first_name VARCHAR(255),
last_name VARCHAR(255),
username VARCHAR(255),
password VARCHAR(255),
email_address VARCHAR(255)
);

CREATE TABLE Product(
id INT PRIMARY KEY,
name VARCHAR(255),
description TEXT,
price DECIMAL,
weight DOUBLE,
category INT NOT NULL REFERENCES ProductCategory(id),
supplier INT NOT NULL REFERENCES Supplier(id),
--FOREIGN KEY (category) REFERENCES ProductCategory(id),
--FOREIGN KEY (supplier) REFERENCES Supplier(id),
image_url TEXT
);

CREATE TABLE Address(

id INT PRIMARY KEY,
country VARCHAR(255) UNIQUE NOT NULL,
city VARCHAR(255) UNIQUE NOT NULL,
county VARCHAR(255) UNIQUE NOT NULL,
street_address VARCHAR(255) UNIQUE NOT NULL

--CONSTRAINT pk_complete_address PRIMARY KEY (country,city,county,street_address)
);
--with TEXT TYPE and w/o UNIQUE didn t worked creating the table "Location"
--DROP TABLE Address;

CREATE TABLE Location (
id INT PRIMARY KEY,
name VARCHAR(255),
address INT NOT NULL REFERENCES Address(id)
--address VARCHAR(255) NOT NULL REFERENCES Address(pk_complete_address)
--address_country VARCHAR(255) NOT NULL REFERENCES Address(country),
--address_city VARCHAR(255) NOT NULL REFERENCES Address(city),
--address_county VARCHAR(255) NOT NULL REFERENCES Address(county),
--address_street_address VARCHAR(255) NOT NULL REFERENCES Address(street_address)

--FOREIGN KEY (address_country) REFERENCES Address(country),
--FOREIGN KEY (address_city) REFERENCES Address(city),
--FOREIGN KEY (address_County) REFERENCES Address(county),
--FOREIGN KEY (address_street_address) REFERENCES Address(street_address)
);



CREATE TABLE Stock(
product INT NOT NULL REFERENCES Product(id),
location INT NOT NULL REFERENCES Location(id),
quantity INT,
CONSTRAINT pk_product_location PRIMARY KEY (product,location)

--foreign key(product) REFERENCES Product(id),
--foreign key (location) REFERENCES Location(id)
);


CREATE TABLE Orders(
id INT PRIMARY KEY,
shipped_from INT NOT NULL REFERENCES Location(id),
customer INT NOT NULL REFERENCES Customer(id),
created_at DATETIME,
address INT NOT NULL REFERENCES Address(id)
);
--address VARCHAR(255) REFERENCES Address(pk_complete_address)
--address_country VARCHAR(255) REFERENCES Address(country),
--address_city VARCHAR(255) REFERENCES Address(city),
--address_county VARCHAR(255) REFERENCES Address(county),
--address_street_address VARCHAR(255) REFERENCES Address(street_address)

--PRIMARY KEY (id),
--FOREIGN KEY (address_country) REFERENCES Address(country),
--FOREIGN KEY (address_city) REFERENCES Address(city),
--FOREIGN KEY (address_county) REFERENCES Address(county),
--FOREIGN KEY (address_street_address) REFERENCES Address(street_address)


CREATE TABLE OrderDetail(
orders INT NOT NULL REFERENCES Orders(id),
product INT NOT NULL REFERENCES Product(id),
quantity INT,
CONSTRAINT pk_order_product PRIMARY KEY (orders,product)
--FOREIGN KEY (orders) REFERENCES Orders(id),
--FOREIGN KEY (product) REFERENCES Product(id)
);

CREATE TABLE Revenue(
id INT PRIMARY KEY,
location INT REFERENCES Location(id),
date DATE,
sum DECIMAL
--FOREIGN KEY (location) REFERENCES Location(id)
);