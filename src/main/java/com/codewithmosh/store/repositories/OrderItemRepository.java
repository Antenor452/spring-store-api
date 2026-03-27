package com.codewithmosh.store.repositories;

import com.codewithmosh.store.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}