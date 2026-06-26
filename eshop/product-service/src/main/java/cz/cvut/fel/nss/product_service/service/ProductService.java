package cz.cvut.fel.nss.product_service.service;

import cz.cvut.fel.nss.product_service.model.dto.AvailabilityResponse;
import cz.cvut.fel.nss.product_service.model.dto.CreateProductRequest;
import cz.cvut.fel.nss.product_service.model.dto.ProductDTO;
import cz.cvut.fel.nss.product_service.model.dto.UpdateProductRequest;
import cz.cvut.fel.nss.product_service.model.entity.Category;
import cz.cvut.fel.nss.product_service.model.entity.Product;
import cz.cvut.fel.nss.product_service.model.utils.ProductFactory;
import cz.cvut.fel.nss.product_service.model.utils.ProductMapper;
import cz.cvut.fel.nss.product_service.repository.CategoryRepository;
import cz.cvut.fel.nss.product_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductFactory productFactory;
    private final ProductMapper productMapper;
    private final ProductCacheService productCacheService;
    private final ProductSearchService productSearchService;

    public ProductDTO createProduct(CreateProductRequest request) {
        if (productRepository.existsByNameIgnoreCase(request.name().trim())) {
            throw new IllegalArgumentException("Product with name '" + request.name() + "' already exists.");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category not found: " + request.categoryId()
                ));

        Product product = productFactory.create(request, category);
        Product savedProduct = productRepository.save(product);
        ProductDTO productDTO = productMapper.toDto(savedProduct);

        productCacheService.saveProductToCache(productDTO);
        productSearchService.indexProduct(savedProduct);

        return productDTO;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        return productCacheService.getProductFromCache(id)
                .orElseGet(() -> {
                    Product product = findProductEntityById(id);
                    ProductDTO productDTO = productMapper.toDto(product);
                    productCacheService.saveProductToCache(productDTO);
                    return productDTO;
                });
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String query, Long categoryId) {
        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId)
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }

        return productSearchService.searchProducts(query);
    }

    public ProductDTO updateProduct(Long id, UpdateProductRequest request) {
        Product product = findProductEntityById(id);

        if (request.name() != null && !request.name().isBlank()) {
            String newName = request.name().trim();

            if (!product.getName().equalsIgnoreCase(newName)
                    && productRepository.existsByNameIgnoreCase(newName)) {
                throw new IllegalArgumentException("Product with name '" + newName + "' already exists.");
            }

            product.setName(newName);
        }

        if (request.description() != null) {
            product.setDescription(request.description());
        }

        if (request.price() != null) {
            product.updatePrice(request.price());
        }

        if (request.stock() != null) {
            product.updateStock(request.stock());
        }

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category not found: " + request.categoryId()
                    ));

            product.setCategory(category);
        }

        Product savedProduct = productRepository.save(product);
        ProductDTO productDTO = productMapper.toDto(savedProduct);

        productCacheService.saveProductToCache(productDTO);
        productSearchService.indexProduct(savedProduct);

        return productDTO;
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found: " + id);
        }

        productRepository.deleteById(id);
        productCacheService.evictProduct(id);
        productSearchService.deleteProductFromIndex(id);
    }

    @Transactional(readOnly = true)
    public AvailabilityResponse checkAvailability(Long productId, int quantity) {
        Product product = findProductEntityById(productId);
        boolean available = product.checkAvailability(quantity);

        return new AvailabilityResponse(productId, quantity, available);
    }

    private Product findProductEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    public ProductDTO decreaseStock(Long productId, int quantity) {
        Product product = findProductEntityById(productId);

        if (!product.checkAvailability(quantity)) {
            throw new IllegalArgumentException("Not enough product stock.");
        }

        product.updateStock(product.getStock() - quantity);

        Product savedProduct = productRepository.save(product);
        ProductDTO productDTO = productMapper.toDto(savedProduct);

        productCacheService.saveProductToCache(productDTO);
        productSearchService.indexProduct(savedProduct);

        return productDTO;
    }
    public ProductDTO updateStock(Long id, Integer amount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        product.setStock(product.getStock() + amount);

        Product saved = productRepository.save(product);
        productSearchService.indexProduct(saved);

        return productMapper.toDto(saved);
    }

    public int reindexProducts() {
        List<Product> products = productRepository.findAll();

        products.forEach(productSearchService::indexProduct);

        return products.size();
    }
}