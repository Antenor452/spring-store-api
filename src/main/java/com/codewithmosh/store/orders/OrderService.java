package com.codewithmosh.store.orders;

import com.codewithmosh.store.authentication.AuthenticationService;
import com.codewithmosh.store.carts.Cart;
import com.codewithmosh.store.exceptions.ForbiddenResourceException;
import com.codewithmosh.store.exceptions.ResourceNotFoundException;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.payments.PaymentStatus;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.users.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;
    private final OrderMapper orderMapper;


    public Order createOrder(User user, Cart cart) {

        var order = Order.builder()
                .customer(user)
                .status(PaymentStatus.PENDING)
                .build();

        List<OrderItem> items = cart.getItems().stream()
                .map(cartItem -> {
                    return OrderItem.builder()
                            .order(order)
                            .quantity(cartItem.getQuantity())
                            .product(cartItem.getProduct())
                            .unitPrice(cartItem.getProduct().getPrice())
                            .totalPrice(cartItem.getTotalPrice())
                            .build();
                }).toList();

        order.setItems(new HashSet<>(items));
        order.setTotalPrice(cart.calculateTotalPrice());

        return orderRepository.save(order);

    }

    public List<Order> getOrdersForCurrentUser() {
        User user = authenticationService.getCurrentUser();
        return orderRepository.getOrdersByUser(user.getId());
    }

    public List<OrderDto> getOrderDtosForCurrentUser() {
        return getOrdersForCurrentUser().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public Order getOrderForCurrentUser(Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        var user = authenticationService.getCurrentUser();

        if (!order.isPlacedBy(user)) throw new ForbiddenResourceException("");

        return order;
    }

    public OrderDto getOrderDtoForCurrentUser(Long orderId) {
        return orderMapper.toDto(getOrderForCurrentUser(orderId));
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public void updateOrderStatus(Long orderId, PaymentStatus status) {
        var order = orderRepository.findById(orderId).orElse(null);

        if (order == null) return;
        if (!order.canTransitionTo(status)) return;

        order.setStatus(status);
        orderRepository.save(order);
    }
}
