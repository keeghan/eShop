package com.keeghan.eShop;

import com.keeghan.eShop.domain.entities.Product;
import com.keeghan.eShop.repositories.ProductRepository;
import com.keeghan.eShop.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void testCreateProduct_Success() {
        Product product = TestDataUtil.createTestProductA();
        ResponseEntity<Product> response = productService.createProduct(product);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(product.getName());
    }

    @Test
    @Transactional
    public void testCreateProduct_NullProduct() {
        ResponseEntity<Product> response = productService.createProduct(null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Transactional
    public void testCreateProduct_InvalidPrice() {
        Product product = TestDataUtil.createTestProductA();
        product.setPrice(BigDecimal.ZERO);
        ResponseEntity<Product> response = productService.createProduct(null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Transactional
    public void testDeleteProduct_Success() {
        Product product = TestDataUtil.createTestProductA();
        productRepository.save(product);
        ResponseEntity<String> response = productService.deleteProduct(product.getProductId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productRepository.existsById(product.getProductId())).isFalse();
    }

    @Test
    @Transactional
    public void testDeleteProduct_NotFound() {
        ResponseEntity<String> response = productService.deleteProduct(999L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}