package cz.cvut.fel.nss.product_service.service;

import cz.cvut.fel.nss.product_service.model.dto.ProductDTO;
import cz.cvut.fel.nss.product_service.model.entity.Product;
import cz.cvut.fel.nss.product_service.model.search.ProductDocument;
import cz.cvut.fel.nss.product_service.model.utils.ProductMapper;
import cz.cvut.fel.nss.product_service.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;
    private final ProductMapper productMapper;

    public void indexProduct(Product product) {
        ProductDocument document = productMapper.toDocument(product);
        productSearchRepository.save(document);
    }

    public void deleteProductFromIndex(Long productId) {
        productSearchRepository.deleteById(productId);
    }

    public List<ProductDTO> searchProducts(String query) {
        if (query == null || query.isBlank()) {
            List<ProductDocument> documents = new ArrayList<>();

            productSearchRepository.findAll()
                    .forEach(documents::add);

            return documents.stream()
                    .map(productMapper::toDto)
                    .toList();
        }

        return productSearchRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public List<ProductDTO> searchProductsByCategoryName(String categoryName) {
        return productSearchRepository.findByCategoryName(categoryName)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
}