package cz.cvut.fel.nss.product_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(

        @NotBlank(message = "Category name is required.")
        @Size(max = 80, message = "Category name can have max 80 characters.")
        String name
) {
}