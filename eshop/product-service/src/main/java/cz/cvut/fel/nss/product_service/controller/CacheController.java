package cz.cvut.fel.nss.product_service.controller;

import cz.cvut.fel.nss.product_service.model.dto.CacheProductResponse;
import cz.cvut.fel.nss.product_service.service.ProductCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final ProductCacheService productCacheService;

    @GetMapping("/products/{id}")
    public CacheProductResponse inspectProductCache(@PathVariable Long id) {
        return productCacheService.inspectProductCache(id);
    }

    @DeleteMapping("/products/{id}")
    public void evictProductCache(@PathVariable Long id) {
        productCacheService.evictProduct(id);
    }
}