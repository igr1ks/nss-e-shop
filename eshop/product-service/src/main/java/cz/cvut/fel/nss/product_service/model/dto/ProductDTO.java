package cz.cvut.fel.nss.product_service.model.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stock,
        Long categoryId,
        String categoryName
) {
}

