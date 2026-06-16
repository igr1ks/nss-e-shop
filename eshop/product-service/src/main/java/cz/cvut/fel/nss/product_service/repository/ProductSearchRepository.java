package cz.cvut.fel.nss.product_service.repository;

import cz.cvut.fel.nss.product_service.model.search.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {

    List<ProductDocument> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description
    );

    List<ProductDocument> findByCategoryName(String categoryName);
}