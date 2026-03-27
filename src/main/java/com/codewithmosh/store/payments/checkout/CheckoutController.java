package com.codewithmosh.store.payments.checkout;

import com.codewithmosh.store.payments.WebhookRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;


    @PostMapping
    ResponseEntity<CheckoutResponse> checkout(
            @Valid @RequestBody CheckoutCartRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var checkoutResponse = checkoutService.checkout(request.getCartId());
        var uri = uriBuilder.path("/orders/{id}").buildAndExpand(checkoutResponse.getOrderId()).toUri();
        return ResponseEntity.created(uri).body(checkoutResponse);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload
    ) {
        System.out.println(headers);
        checkoutService.handleWebhookEvents(
                new WebhookRequest(
                        headers,
                        payload
                ));


    }
}
