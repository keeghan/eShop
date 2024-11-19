package com.keeghan.eShop.controllers;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.domain.entities.dtos.CategoryDTO;
import com.keeghan.eShop.domain.mappers.CategoryMapperImpl;
import com.keeghan.eShop.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    private final CategoryMapperImpl categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapperImpl categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return categories.stream().map(categoryMapper::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategories(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return categoryMapper.mapToDto(category);
    }

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category savedCategory = categoryService.createCategory(categoryDTO.getName());
        return ResponseEntity
                .created(URI.create("/api/v1/categories/" + savedCategory.getCategoryId()))
                .body(categoryMapper.mapToDto(savedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        Category updatedCategory = categoryService.updateCategoryName(categoryId, categoryDTO.getName());
        if (updatedCategory == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryMapper.mapToDto(updatedCategory));
    }

}