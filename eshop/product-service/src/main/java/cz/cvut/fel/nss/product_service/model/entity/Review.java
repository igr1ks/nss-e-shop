package cz.cvut.fel.nss.product_service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends AbstractEntity {

    @Column(nullable = false)
    private int rating;

    @Column(length = 1000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void editReview(String newComment) {
        this.comment = newComment;
    }
}