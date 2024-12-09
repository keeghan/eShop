package com.keeghan.eShop.controllers;

import com.keeghan.eShop.domain.dtos.ProductDTO;
import com.keeghan.eShop.domain.entities.Product;
import com.keeghan.eShop.domain.mappers.ProductMapperImpl;
import com.keeghan.eShop.exception.GenericRequestException;
import com.keeghan.eShop.service.ProductService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductMapperImpl productMapper;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductMapperImpl productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Product> productPage = productService.getAllProducts(pageable);
            Page<ProductDTO> productDTOPage = productPage.map(productMapper::mapToDto);
            return ResponseEntity.ok(productDTOPage);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.mapToEntity(productDTO);
        validateProduct(product);
        ProductDTO savedProductDTO = productMapper.mapToDto(productService.createProduct(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long productId) {
        boolean isDeleted = productService.deleteProduct(productId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductDTO updatedProductDTO) {
        Product existingProduct = productService.getProductById(productId);
        if (existingProduct == null) return ResponseEntity.notFound().build();
        existingProduct.setName(updatedProductDTO.getName());
        existingProduct.setDescription(updatedProductDTO.getDescription());
        existingProduct.setPrice(updatedProductDTO.getPrice());
        existingProduct.setStockQuantity(updatedProductDTO.getStockQuantity());
        validateProduct(existingProduct);
        Product updatedProduct = productService.updateProduct(existingProduct);
        return ResponseEntity.ok(productMapper.mapToDto(updatedProduct));
    }

    @Transactional()
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(productMapper.mapToDto(productService.getProductById(productId)));
    }

    public void validateProduct(Product product) {
        if (product == null) {
            throw new GenericRequestException("Product cannot be null");
        }
        if (StringUtils.isBlank(product.getName())) {
            throw new GenericRequestException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericRequestException("Product price must be greater than zero");
        }
        if (product.getStockQuantity() == null || product.getStockQuantity() < 0) {
            throw new GenericRequestException("Stock quantity cannot be negative");
        }
    }
}
