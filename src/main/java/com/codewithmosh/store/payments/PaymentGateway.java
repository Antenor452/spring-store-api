package com.codewithmosh.store.payments;

import com.codewithmosh.store.orders.Order;
import com.codewithmosh.store.payments.checkout.CheckoutSession;

import java.util.Optional;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order);

    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
