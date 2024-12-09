package com.keeghan.eShop.controllers;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.domain.entities.dtos.CategoryDTO;
import com.keeghan.eShop.domain.mappers.CategoryMapperImpl;
import com.keeghan.eShop.service.CategoryService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    private final CategoryMapperImpl categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapperImpl categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories.stream().map(categoryMapper::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(categoryMapper.mapToDto(category));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category savedCategory = categoryService.createCategory(categoryDTO.getName());
        return ResponseEntity.created(URI.create("/api/v1/categories/")).body(categoryMapper.mapToDto(savedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        Category updatedCategory = categoryService.updateCategoryName(categoryId, categoryDTO.getName());
        if (updatedCategory == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryMapper.mapToDto(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long categoryId) {
        boolean isDeleted = categoryService.deleteCategory(categoryId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
