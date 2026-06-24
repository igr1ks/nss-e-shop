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
        String name = request.name().trim();

        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Category with name '" + name + "' already exists.");
        }

        Category category = new Category(name);
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = findCategoryEntityById(id);
        String newName = request.name().trim();

        if (!category.getName().equalsIgnoreCase(newName)
                && categoryRepository.existsByNameIgnoreCase(newName)) {
            throw new IllegalArgumentException("Category with name '" + newName + "' already exists.");
        }

        category.setName(newName);

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