package cz.cvut.fel.nss.product_service.model.utils;

import cz.cvut.fel.nss.product_service.model.dto.ProductDTO;
import cz.cvut.fel.nss.product_service.model.entity.Product;
import cz.cvut.fel.nss.product_service.model.search.ProductDocument;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }

    public ProductDTO toDto(ProductDocument document) {
        return new ProductDTO(
                document.getId(),
                document.getName(),
                document.getDescription(),
                document.getPrice(),
                document.getStock(),
                document.getCategoryId(),
                document.getCategoryName()
        );
    }

    public ProductDocument toDocument(Product product) {
        return new ProductDocument(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}