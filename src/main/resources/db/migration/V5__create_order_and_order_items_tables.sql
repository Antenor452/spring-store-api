CREATE TABLE orders
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    customer_id BIGINT                            NOT NULL,
    status      VARCHAR(20)                       NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10, 2)                    NOT NULL DEFAULT 0

);

CREATE TABLE order_items
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    order_id    BIGINT                            NOT NULL,
    product_id  BIGINT                            NOT NULL,
    unit_price  DECIMAL(10, 2)                    NOT NULL CHECK ( unit_price >= 0 ),
    quantity    INT                               NOT NULL CHECK ( quantity > 0 ),
    total_price DECIMAL(10, 2)                    NOT NULL CHECK ( total_price >= 0 )

);


ALTER TABLE orders
    ADD CONSTRAINT orders_customer_id_fk FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE order_items
    ADD CONSTRAINT order_items_order_id_products_unique UNIQUE (order_id, product_id),
    ADD CONSTRAINT order_items_order_id_fk FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    ADD CONSTRAINT order_items_product_id_fk FOREIGN KEY (product_id) REFERENCES products (id);

CREATE INDEX idx_orders_customer_id ON orders (customer_id);
CREATE INDEX idx_order_items_product_id ON order_items (product_id);


