package cz.cvut.fel.nss.product_service.model.utils;

import cz.cvut.fel.nss.product_service.model.entity.Category;
import cz.cvut.fel.nss.product_service.model.entity.Inventory;
import cz.cvut.fel.nss.product_service.model.entity.Product;
import cz.cvut.fel.nss.product_service.model.dto.CreateProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    public Product create(CreateProductRequest request, Category category) {
        Product product = new Product();

        product.setName(request.name().trim());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setCategory(category);

        Inventory inventory = new Inventory(product, request.stock());
        product.setInventory(inventory);

        return product;
    }
}