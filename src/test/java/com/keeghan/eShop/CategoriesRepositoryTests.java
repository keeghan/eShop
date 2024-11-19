package com.keeghan.eShop;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoriesRepositoryTests {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoriesRepositoryTests(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Test
    @Transactional
    public void testThatCategoryCanBeCreatedAndCalled() {
        Category category = TestDataUtil.createTestCategoryA();
        categoryRepository.save(category);
        Optional<Category> result = categoryRepository.findById(category.getCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(category);
    }

    @Test
    @Transactional
    public void shouldUpdateCategoryName() {
        // Arrange: Create and save a new category
        Category category = TestDataUtil.createTestCategoryA();
        Category savedCategory = categoryRepository.save(category);

        // Act: Update the category name
        savedCategory.setName("New name");
        categoryRepository.save(savedCategory);

        // Assert: Verify the category name is updated correctly
        Optional<Category> updatedCategory = categoryRepository.findById(savedCategory.getCategoryId());

        assertThat(updatedCategory).isPresent();
        assertThat(updatedCategory.get().getName()).isEqualTo("New name");
        assertThat(updatedCategory.get()).isEqualTo(savedCategory);
    }



}
