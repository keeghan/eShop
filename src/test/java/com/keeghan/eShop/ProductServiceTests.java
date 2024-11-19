package com.keeghan.eShop;

import com.keeghan.eShop.domain.dtos.ProductDTO;
import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.domain.entities.Product;
import com.keeghan.eShop.domain.mappers.ProductMapperImpl;
import com.keeghan.eShop.repositories.ProductRepository;
import com.keeghan.eShop.service.CategoryService;
import com.keeghan.eShop.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapperImpl productMapper;

    @Test
    public void testCreateProduct_Success() {
        Category category = TestDataUtil.createTestCategoryA();
        categoryService.createCategoryWithoutId(category);

        Product product = TestDataUtil.createTestProductA();
        ProductDTO productDTO = productMapper.mapToDto(product);
        ProductDTO response = productMapper.mapToDto(productService.createProduct(product));

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(productDTO.getName());
        assertThat(response.getProductId()).isEqualTo(productDTO.getProductId());
        assertThat(response.getDescription()).isEqualTo(productDTO.getDescription());
        assertThat(response.getStockQuantity()).isEqualTo(productDTO.getStockQuantity());
        assertThat(response.getCategoryId()).isEqualTo(productDTO.getCategoryId());
    }

    @Test
    @Transactional
    public void testCreateProduct_NullProduct() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> productService.createProduct(null));
    }

    @Test
    @Transactional
    public void testDeleteProduct_Success() {
        Product product = TestDataUtil.createTestProductA();
        productService.createProduct(product);
        boolean response = productService.deleteProduct(product.getProductId());
        assertThat(response).isEqualTo(true);
        Product deletedProduct = productService.getProductById(product.getProductId());
        assertThat(deletedProduct).isNull();
    }

    @Test
    @Transactional
    public void testDeleteProduct_NotFound() {
        Product product = TestDataUtil.createTestProductA();
        boolean response = productService.deleteProduct(product.getProductId());
        assertThat(response).isEqualTo(false);
    }
}