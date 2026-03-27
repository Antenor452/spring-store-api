package com.codewithmosh.store.orders;

import com.codewithmosh.store.payments.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private PaymentStatus status;
    private Instant createdAt;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;


}
