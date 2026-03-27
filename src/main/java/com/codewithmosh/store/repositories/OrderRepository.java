package com.codewithmosh.store.repositories;

import com.codewithmosh.store.orders.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT  o FROM Order o WHERE o.id=:orderId")
    Optional<Order> getOrderWithItems(@Param("orderId") Long orderId);

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order  o WHERE o.customer.id = :userId")
    List<Order> getOrdersByUser(@Param("userId") Long userId);
}