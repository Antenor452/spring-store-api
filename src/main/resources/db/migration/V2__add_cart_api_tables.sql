CREATE TABLE cart (
    id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())) ,
    date_created DATE DEFAULT (CURRENT_DATE),
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT  NOT NULL,
    cart_id BINARY(16) NOT NULL ,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

ALTER TABLE cart_items
    ADD CONSTRAINT cart_products_unique UNIQUE(cart_id,product_id),
    ADD CONSTRAINT cart_items_cart_id_fk FOREIGN KEY  (cart_id) REFERENCES  cart(id) ON DELETE CASCADE ,
    ADD CONSTRAINT cart_items_product_id_fk FOREIGN KEY  (product_id) REFERENCES  products(id);

CREATE INDEX idx_cart_items_product_id ON cart_items(product_id);