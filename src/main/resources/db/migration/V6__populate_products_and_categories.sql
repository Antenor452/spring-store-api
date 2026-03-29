INSERT INTO categories (name)
VALUES ('Electronics'),
       ('Books'),
       ('Clothing'),
       ('Home');


INSERT INTO products (name, description, price, category_id)
VALUES

-- Electronics (category_id = 1)
('Laptop', 'High performance laptop', 1200, 1),
('Smartphone', 'Latest smartphone', 800, 1),
('Tablet', 'Portable tablet', 500, 1),
('Headphones', 'Noise cancelling headphones', 200, 1),
('Camera', 'Digital camera', 650, 1),

-- Books (category_id = 2)
('Novel', 'Fiction novel', 20, 2),
('Cookbook', 'Cooking recipes', 25, 2),
('History Book', 'World history', 30, 2),
('Programming Book', 'Learn Java', 45, 2),
('Notebook', 'Writing notebook', 10, 2),

-- Clothing (category_id = 3)
('T-Shirt', 'Cotton t-shirt', 15, 3),
('Jeans', 'Blue jeans', 40, 3),
('Jacket', 'Winter jacket', 120, 3),
('Sneakers', 'Running sneakers', 75, 3),
('Cap', 'Baseball cap', 12, 3),

-- Home (category_id = 4)
('Chair', 'Wooden chair', 60, 4),
('Table', 'Dining table', 250, 4),
('Lamp', 'Desk lamp', 35, 4),
('Sofa', 'Comfortable sofa', 700, 4),
('Coffee Maker', 'Automatic coffee maker', 90, 4);