package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @CreationTimestamp
    @Column(name = "date_created", insertable = false, updatable = false)
    private Instant dateCreated;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal calculateTotalPrice() {
        if (items == null) return BigDecimal.valueOf(0);

        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);

    }

    public CartItem getItem(Long productId) {
        return items
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);
    }


    public CartItem addItem(Product product) {
        var cartItem = getItem(product.getId());

        if (cartItem != null) {
            cartItem.increaseQuantity(1);
        } else {
            cartItem = CartItem.builder()
                    .product(product)
                    .cart(this)
                    .quantity(1)
                    .build();

            items.add(cartItem);
        }

        return cartItem;
    }

    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clear() {
        items.clear();
    }

}