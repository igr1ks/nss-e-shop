package cz.cvut.fel.nss.product_service.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest(

        @Size(max = 150, message = "Product name can have max 150 characters.")
        String name,

        @Size(max = 1000, message = "Description can have max 1000 characters.")
        String description,

        @DecimalMin(value = "0.01", message = "Price must be greater than zero.")
        BigDecimal price,

        @Min(value = 0, message = "Stock cannot be negative.")
        Integer stock,

        Long categoryId
) {
}