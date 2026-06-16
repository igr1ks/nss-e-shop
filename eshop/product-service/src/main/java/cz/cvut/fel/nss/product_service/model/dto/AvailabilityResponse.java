package cz.cvut.fel.nss.product_service.model.dto;

public record AvailabilityResponse(
        Long productId,
        int requestedQuantity,
        boolean available
) {
}