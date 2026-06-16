package cz.cvut.fel.nss.product_service.model.dto;

public record ReviewDTO(
        Long id,
        int rating,
        String comment,
        Long productId
) {
}