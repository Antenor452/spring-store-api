package com.codewithmosh.store.payments.checkout;

import com.codewithmosh.store.exceptions.BadRequestException;
import com.codewithmosh.store.payments.PaymentGateway;
import com.codewithmosh.store.payments.WebhookRequest;
import com.codewithmosh.store.authentication.AuthenticationService;
import com.codewithmosh.store.carts.CartService;
import com.codewithmosh.store.orders.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CheckoutService {

    private final OrderService orderService;
    private final CartService cartService;
    private final AuthenticationService authenticationService;
    private final PaymentGateway paymentGateway;


    public CheckoutResponse checkout(UUID cartId) {
        var cart = cartService.getCart(cartId);
        if (cart.getItems().isEmpty()) throw new BadRequestException("Cart is empty");
        var user = authenticationService.getCurrentUser();

        var order = orderService.createOrder(user, cart);


        var checkoutSession = paymentGateway.createCheckoutSession(order);
        cartService.clearCart(cartId);

        return new CheckoutResponse(order.getId(), checkoutSession.getCheckoutUrl());


    }

    public void handleWebhookEvents(WebhookRequest request) {
        paymentGateway.parseWebhookRequest(request)
                .ifPresent(paymentResult -> orderService.updateOrderStatus(
                        paymentResult.getOrderId(),
                        paymentResult.getPaymentStatus())
                );

    }
}
