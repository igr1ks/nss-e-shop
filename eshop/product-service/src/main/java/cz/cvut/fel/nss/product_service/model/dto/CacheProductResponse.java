package cz.cvut.fel.nss.product_service.model.dto;

public record CacheProductResponse(
        Long productId,
        boolean cached,
        ProductDTO product
) {
}