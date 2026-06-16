package cz.cvut.fel.nss.product_service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public Inventory(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        this.quantity += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        if (!checkAvailability(amount)) {
            throw new IllegalArgumentException("Not enough product stock.");
        }

        this.quantity -= amount;
    }

    public boolean checkAvailability(int requestedQuantity) {
        return requestedQuantity > 0 && this.quantity >= requestedQuantity;
    }
}