package com.codewithmosh.store.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartItemRequest {
    @NotNull(message = "Product Id is required")
    private Long productId;
}
