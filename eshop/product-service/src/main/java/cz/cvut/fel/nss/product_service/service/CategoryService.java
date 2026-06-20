package cz.cvut.fel.nss.product_service.service;

import cz.cvut.fel.nss.product_service.model.dto.CategoryDTO;
import cz.cvut.fel.nss.product_service.model.dto.CreateCategoryRequest;
import cz.cvut.fel.nss.product_service.model.dto.UpdateCategoryRequest;
import cz.cvut.fel.nss.product_service.model.entity.Category;
import cz.cvut.fel.nss.product_service.model.utils.CategoryMapper;
import cz.cvut.fel.nss.product_service.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = findCategoryEntityById(id);
        return categoryMapper.toDto(category);
    }

    public CategoryDTO createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException("Category already exists: " + request.name());
        }

        Category category = new Category(request.name().trim());
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = findCategoryEntityById(id);

        if (!category.getName().equalsIgnoreCase(request.name())
                && categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException("Category already exists: " + request.name());
        }

        category.setName(request.name().trim());

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = findCategoryEntityById(id);

        if (!category.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete category with existing products.");
        }

        categoryRepository.delete(category);
    }

    private Category findCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));
    }
}