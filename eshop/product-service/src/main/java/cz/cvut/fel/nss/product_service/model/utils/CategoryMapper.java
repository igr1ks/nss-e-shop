package cz.cvut.fel.nss.product_service.model.utils;

import cz.cvut.fel.nss.product_service.model.dto.CategoryDTO;
import cz.cvut.fel.nss.product_service.model.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}
