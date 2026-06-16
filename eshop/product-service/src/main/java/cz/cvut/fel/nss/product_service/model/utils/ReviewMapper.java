package cz.cvut.fel.nss.product_service.model.utils;

import cz.cvut.fel.nss.product_service.model.dto.ReviewDTO;
import cz.cvut.fel.nss.product_service.model.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getProduct().getId()
        );
    }
}