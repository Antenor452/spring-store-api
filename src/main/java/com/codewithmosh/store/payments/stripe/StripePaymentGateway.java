package com.codewithmosh.store.payments.stripe;

import com.codewithmosh.store.orders.Order;
import com.codewithmosh.store.orders.OrderItem;
import com.codewithmosh.store.products.Product;
import com.codewithmosh.store.payments.*;
import com.codewithmosh.store.payments.checkout.CheckoutSession;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {


    @Value("${clientUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecret}")
    private String webhookSecret;

    private static SessionCreateParams.PaymentIntentData setPaymentIntentMetaData(Order order) {
        return SessionCreateParams.PaymentIntentData.builder()
                .putMetadata("order_id", order.getId().toString())
                .build();
    }

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {

            var sessionParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success/order?" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .addAllLineItem(createLineItems(order)
                    )
                    .setPaymentIntentData(setPaymentIntentMetaData(order))
                    .build();

            var session = Session.create(sessionParams);

            return new CheckoutSession(session.getUrl());

        } catch (StripeException ex) {
            System.out.println(ex.getMessage());
            throw new PaymentException("Error creating checkout session");
        }
    }

    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {

            var signature = request.getHeaders().get("stripe-signature");
            var payload = request.getPayload();
            var event = Webhook.constructEvent(
                    payload,
                    signature,
                    webhookSecret
            );
            System.out.println(event.getType());
//            var orderId = extractOrderId(event);
            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));
                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));
                default -> Optional.empty();
            };

        } catch (SignatureVerificationException exception) {
            System.out.println(exception.getMessage());
            throw new PaymentException("Invalid Signature");
        }
    }

    private Long extractOrderId(Event event) {
        var paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject()
                .orElseThrow(() -> new PaymentException("Could not deserialize stripe event.Check SDK and API version"));

        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(Product product) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(product.getName())
                .setDescription(product.getDescription())
                .build();
    }

    private List<SessionCreateParams.LineItem> createLineItems(Order order) {
        return order
                .getItems()
                .stream()
                .map(this::createLineItem
                ).toList();
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(
                        buildPriceData(item)
                )
                .build();
    }

    private SessionCreateParams.LineItem.PriceData buildPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice()
                        .multiply(BigDecimal.valueOf(100))
                ).setProductData(
                        createProductData(item.getProduct())
                ).build();
    }
}
