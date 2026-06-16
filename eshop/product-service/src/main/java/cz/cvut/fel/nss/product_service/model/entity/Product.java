package cz.cvut.fel.nss.product_service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero.");
        }
        this.price = newPrice;
    }

    public void updateStock(int newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative.");
        }

        this.stock = newStock;

        if (this.inventory != null) {
            this.inventory.setQuantity(newStock);
        }
    }

    public boolean checkAvailability(int quantity) {
        if (inventory != null) {
            return inventory.checkAvailability(quantity);
        }

        return quantity > 0 && this.stock >= quantity;
    }
}