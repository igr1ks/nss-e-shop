package cz.cvut.fel.nss.order_service.client;

import cz.cvut.fel.nss.order_service.dto.AvailabilityResponse;
import cz.cvut.fel.nss.order_service.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductDto getProductById(Long productId) {
        return restTemplate.getForObject(
                productServiceUrl + "/products/" + productId,
                ProductDto.class
        );
    }

    public boolean isAvailable(Long productId, int quantity) {
        AvailabilityResponse response = restTemplate.getForObject(
                productServiceUrl + "/products/" + productId + "/availability?quantity=" + quantity,
                AvailabilityResponse.class
        );

        return response != null && response.isAvailable();
    }

    public void decreaseStock(Long productId, int quantity) {
        restTemplate.postForObject(
                productServiceUrl + "/products/" + productId + "/stock/decrease?quantity=" + quantity,
                null,
                ProductDto.class
        );
    }
}