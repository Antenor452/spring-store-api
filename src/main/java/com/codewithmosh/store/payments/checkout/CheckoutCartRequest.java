package com.codewithmosh.store.payments.checkout;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutCartRequest {
    @NotNull(message = "cartId is required")
    private UUID cartId;
}
