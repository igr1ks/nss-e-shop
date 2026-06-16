package cz.cvut.fel.nss.product_service.controller;

import cz.cvut.fel.nss.product_service.model.dto.CategoryDTO;
import cz.cvut.fel.nss.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}