package com.codewithmosh.store.mappers;

import com.codewithmosh.store.orders.OrderDto;
import com.codewithmosh.store.orders.OrderItemDto;
import com.codewithmosh.store.orders.OrderItemProductDto;
import com.codewithmosh.store.orders.Order;
import com.codewithmosh.store.orders.OrderItem;
import com.codewithmosh.store.products.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    OrderDto toDto(Order Order);


    @Mapping(target = "product", source = "product")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    OrderItemProductDto toProductDto(Product product);
}
