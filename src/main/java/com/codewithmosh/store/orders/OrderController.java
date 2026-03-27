package com.codewithmosh.store.orders;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping
    List<OrderDto> getOrders() {
        return orderService.getOrderDtosForCurrentUser();
    }

    @GetMapping("/{orderId}")
    OrderDto getOrder(
            @PathVariable Long orderId
    ) {
        return orderService.getOrderDtoForCurrentUser(orderId);
    }
}
