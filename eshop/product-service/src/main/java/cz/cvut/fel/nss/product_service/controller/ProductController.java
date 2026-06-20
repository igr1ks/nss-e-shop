package cz.cvut.fel.nss.product_service.controller;

import cz.cvut.fel.nss.product_service.model.dto.AvailabilityResponse;
import cz.cvut.fel.nss.product_service.model.dto.ProductDTO;
import cz.cvut.fel.nss.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long categoryId
    ) {
        return productService.searchProducts(query, categoryId);
    }

    @GetMapping("/{id}/availability")
    public AvailabilityResponse checkAvailability(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        return productService.checkAvailability(id, quantity);
    }

    @PostMapping("/{id}/stock/decrease")
    public ProductDTO decreaseStock(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        return productService.decreaseStock(id, quantity);
    }
}