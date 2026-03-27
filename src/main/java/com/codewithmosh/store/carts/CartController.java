package com.codewithmosh.store.carts;

import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.products.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartService cartService;


    @PostMapping
    ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @RequestMapping("/{id}")
    ResponseEntity<CartDto> getCart(@PathVariable UUID id) {
        var cartDto = cartService.getCartDto(id);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/{id}/items")
    ResponseEntity<?> addToCart(
            @PathVariable UUID id,
            @RequestBody @Valid AddCartItemRequest request
    ) {
        var cartItemDto = cartService.addItemToCart(id, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    ResponseEntity<?> updateCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateCartItemRequest request
    ) {
        var cartItemDto = cartService.updateItem(cartId, productId, request.getQuantity());

        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    ResponseEntity<?> removeCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ) {
        cartService.removeItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{cartId}")
    ResponseEntity<?> clearCart(
            @PathVariable UUID cartId
    ) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("Error", "Cart not found"));

    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("Error", "Product was not found in cart"));

    }


}