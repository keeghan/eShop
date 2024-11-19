package com.keeghan.eShop.service;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElse(null);
    }

    public List<Category> getCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false).toList();
    }

    //Todo: Learn about Lombok builder collection immutability
    public Category createCategory(String categoryName) {
        Category newCategory = new Category(null, categoryName, new HashSet<>());
        return categoryRepository.save(newCategory);
    }

    @Transactional
    public Category updateCategoryName(Long categoryId, String newName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(newName);
        return categoryRepository.save(category);
    }
}
