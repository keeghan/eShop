package com.keeghan.eShop;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.domain.entities.Product;

import java.math.BigDecimal;

public class TestDataUtil {
    public static Category createTestCategoryA() {
        return Category.builder().categoryId(1L).name("Electronics").build();
    }

    public static Category createTestCategoryC() {
        return Category.builder().categoryId(3L).name("Food Item").build();
    }

    public static Category createTestCategoryB() {
        return Category.builder().categoryId(2L).name("Home Appliances").build();
    }

    public static Product createTestProductA() {
        return Product.builder().productId(1L).name("Android Phone QTE").price(BigDecimal.valueOf(22L))
                .category(createTestCategoryA()).stockQuantity(5)
                .description("A brand New Android Phone").build();
    }

    public static Product createTestProductB() {
        return Product.builder().productId(2L).name("Yam").price(BigDecimal.valueOf(22L))
                .category(createTestCategoryC()).stockQuantity(2)
                .description("A food item").build();
    }
}
