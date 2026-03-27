package com.codewithmosh.store.mappers;

import com.codewithmosh.store.payments.checkout.CheckoutResponse;
import com.codewithmosh.store.orders.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {

    CheckoutResponse toDto(Order order);
}
