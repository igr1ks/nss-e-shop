package cz.cvut.fel.nss.order_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends AbstractEntity {

    @Column(nullable = false)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    public Payment(Long orderId, PaymentType type) {
        this.orderId = orderId;
        this.type = type;
    }
}