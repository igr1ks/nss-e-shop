package cz.cvut.fel.nss.product_service.repository;

import cz.cvut.fel.nss.product_service.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);
}