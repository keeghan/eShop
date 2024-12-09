package com.keeghan.eShop.service;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.exception.GenericRequestException;
import com.keeghan.eShop.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GenericRequestException("Category does not Exists", HttpStatus.NOT_FOUND));
    }

    public List<Category> getCategories() {
        List<Category> categories = StreamSupport.stream(categoryRepository.findAll().spliterator(), false).toList();
        if (categories.isEmpty()) {
            throw new GenericRequestException("Category does not Exists", HttpStatus.NOT_FOUND);
        } else {
            return categories;
        }
    }

    //Todo: Learn about Lombok builder collection immutability
    public Category createCategory(String categoryName) {
        Category newCategory = new Category(null, categoryName, new HashSet<>());
        return categoryRepository.save(newCategory);
    }

    public Category createCategoryWithoutId(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategoryName(Long categoryId, String newName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(newName);
        return categoryRepository.save(category);
    }

    @Transactional()
    public boolean deleteCategory(Long productId) {
        return categoryRepository.findById(productId)
                .map(product -> {
                    categoryRepository.delete(product);
                    return true;
                })
                .orElseGet(() -> {
                    return false;
                });
    }
}
