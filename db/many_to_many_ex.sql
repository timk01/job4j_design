CREATE TABLE suppliers (
    supplier_id INT PRIMARY KEY,
    supplier_name VARCHAR(50) NOT NULL,
    address text
);

CREATE TABLE buyers (
    buyer_id INT PRIMARY KEY,
    buyer_name VARCHAR(50) NOT NULL,
    address text
);

CREATE TABLE supplier_buyer_relation (
    relation_id INT PRIMARY KEY,
    supplier_id INT,
    buyer_id INT,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    FOREIGN KEY (buyer_id) REFERENCES buyers(buyer_id),
    UNIQUE (supplier_id, buyer_id)
);