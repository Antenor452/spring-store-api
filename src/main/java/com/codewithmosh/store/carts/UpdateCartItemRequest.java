package com.codewithmosh.store.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UpdateCartItemRequest {

    @NotNull(message = "Quantity is required")
    @Range(min = 0, max = 100, message = "Quantity must be between 1 and 100")
    private Integer quantity;
}
