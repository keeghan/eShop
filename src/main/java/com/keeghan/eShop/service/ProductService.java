package com.keeghan.eShop.service;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.domain.entities.Product;
import com.keeghan.eShop.repositories.CategoryRepository;
import com.keeghan.eShop.repositories.ProductRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(rollbackOn = DataIntegrityViolationException.class)
    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            logger.error("Product creation: {}", e.getMessage());
            throw new DataIntegrityViolationException("Product already exists with the provided details.");
        }
    }


    public Product updateProduct(Product updatedProduct) {
        return productRepository.save(updatedProduct);
    }

    @Transactional()
    public boolean deleteProduct(Long productId) {
        try {
            return productRepository.findById(productId)
                    .map(product -> {
                        productRepository.delete(product);
                        return true;
                    })
                    .orElseGet(() -> {
                        return false;
                    });
        } catch (DataAccessException e) {
            logger.error("Error deleting product with ID {}", productId, e);
            throw new ServiceException("Error deleting product. Please try again later.", e);
        }
    }


    @Transactional()
    public Product getProductById(Long productId) {
        Optional<Product> product =  productRepository.findById(productId);
        return product.orElse(null);
    }
}
