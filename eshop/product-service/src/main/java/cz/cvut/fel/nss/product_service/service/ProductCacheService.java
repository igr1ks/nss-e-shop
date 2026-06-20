package cz.cvut.fel.nss.product_service.service;

import cz.cvut.fel.nss.product_service.model.dto.CacheProductResponse;
import cz.cvut.fel.nss.product_service.model.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCacheService {

    private static final String PRODUCT_CACHE_PREFIX = "product:";

    private final RedisTemplate<String, ProductDTO> productRedisTemplate;

    @Value("${app.cache.product-ttl-minutes:10}")
    private long productTtlMinutes;

    public Optional<ProductDTO> getProductFromCache(Long productId) {
        ProductDTO product = productRedisTemplate.opsForValue().get(buildProductKey(productId));
        return Optional.ofNullable(product);
    }

    public void saveProductToCache(ProductDTO product) {
        productRedisTemplate.opsForValue().set(
                buildProductKey(product.id()),
                product,
                Duration.ofMinutes(productTtlMinutes)
        );
    }

    public void evictProduct(Long productId) {
        productRedisTemplate.delete(buildProductKey(productId));
    }

    public CacheProductResponse inspectProductCache(Long productId) {
        ProductDTO product = productRedisTemplate.opsForValue().get(buildProductKey(productId));
        return new CacheProductResponse(productId, product != null, product);
    }

    private String buildProductKey(Long productId) {
        return PRODUCT_CACHE_PREFIX + productId;
    }
}