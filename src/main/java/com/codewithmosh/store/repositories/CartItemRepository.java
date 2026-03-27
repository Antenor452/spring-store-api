package com.codewithmosh.store.repositories;

import com.codewithmosh.store.carts.Cart;
import com.codewithmosh.store.carts.CartItem;
import com.codewithmosh.store.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductAndCart(Product product, Cart cart);

    List<CartItem> findByCartAndProductIdIn(Cart cart, List<Long> product_id);

    Optional<CartItem> findByCartIdAndProductId(UUID cartId, Long productId);
}