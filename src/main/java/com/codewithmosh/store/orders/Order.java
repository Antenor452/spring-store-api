package com.codewithmosh.store.orders;


import com.codewithmosh.store.payments.PaymentStatus;
import com.codewithmosh.store.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @CreationTimestamp
    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private Set<OrderItem> items = new HashSet<>();


    public boolean isPlacedBy(User customer) {
        return this.customer.equals(customer);
    }

    public boolean canTransitionTo(PaymentStatus next) {
        if (status == next) return false;

        return switch (status) {
            case PENDING -> next == PaymentStatus.PAID || next == PaymentStatus.FAILED;
            case FAILED -> next == PaymentStatus.PAID;
            default -> false;
        };

    }
}
